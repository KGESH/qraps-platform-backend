package qraps.platform.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.review.dto.DirectExcelMapperDto;
import qraps.platform.review.dto.ResponseReviewDto;
import qraps.platform.review.dto.ReviewDto;
import qraps.platform.review.repository.DiodeRepository;
import qraps.platform.review.repository.StepDownICRepository;
import qraps.platform.review.repository.TransistorRepository;
import qraps.platform.utils.EntityHelper;
import qraps.platform.utils.ParsedExcel;
import qraps.platform.web.controller.dto.ReviewPageDto;
import qraps.platform.web.controller.dto.ValidateTarget;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DirectReviewService {

    private final ExcelService excelService;

    private final ValidationService validationService;

    @Autowired
    private StepDownICRepository stepdownIcRepository;


    @Autowired
    private TransistorRepository transistorRepository;


    @Autowired
    private DiodeRepository diodeRepository;


    @Autowired
    public DirectReviewService(ExcelService excelService, ValidationService validationService) {
        this.excelService = excelService;
        this.validationService = validationService;
    }


    /**
     * Todo: Partlist 테이블 변경되면 수정
     */
    public ResponseReviewDto directReview(ValidateTarget target, MultipartFile file) throws IOException {
        ParsedExcel parsedExcel = excelService.parseExcel(file.getInputStream());
        String partNo = parsedExcel.getPartNo();

        ReviewPageDto pageDto = ReviewPageDto.builder()
                .partNo(partNo)
                .validTarget(target)
                .build();

        Object criteriaEntity = validationService.getCriteriaEntity(pageDto.getValidTarget());
        Object foundEntity = validationService.findEntity(pageDto);

        Map<String, Object> criteria = EntityHelper.convertEntityToMap(criteriaEntity);
        Map<String, Object> referenceValue = EntityHelper.convertEntityToMap(foundEntity);

        ReviewDto.Verification verificationDto = ReviewDto.Verification.builder()
                .criteria(criteria)
                .target(referenceValue)
                .build();

        List<ReviewDto.Result> reviewResults = reviewPart(parsedExcel.getParsedRows(), verificationDto);
        return ResponseReviewDto.builder()
                .partNo(target.getTarget())
                .reviewResults(reviewResults)
                .passReview(validatePassReview(reviewResults))
                .build();
    }


    // 설계값과 데이터베이스에 저장된 기준 값 검증
    private List<ReviewDto.Result> reviewPart(List<DirectExcelMapperDto> parsedRows, ReviewDto.Verification verificationDto) {

        return parsedRows.stream()
                .filter(row -> row.needValidate())
                .map(row -> {
                    String partName = row.getPartName();
                    String designValue = row.getDesignValue().orElse(null).toString();

                    boolean validationResult = validationService.validateDesignValue(partName, verificationDto, designValue);
                    return ReviewDto.Result.builder()
                            .partName(row.getPartName())
                            .designValue(row.getDesignValue())
                            .verification(validationResult)
                            .build();
                })
                .collect(Collectors.toList());
    }


    // 모든 rows 중 검증값이 하나라도 맞지 않으면 최종 검증 결과 FAIL
    private boolean validatePassReview(List<ReviewDto.Result> reviewResults) {
        return reviewResults.stream().allMatch(ReviewDto.Result::isVerification);
    }

}
