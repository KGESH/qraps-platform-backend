package qraps.platform.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.review.dto.ExcelMapperDto;
import qraps.platform.review.dto.ResponseReviewDto;
import qraps.platform.review.dto.ReviewDto;
import qraps.platform.review.repository.DiodeRepository;
import qraps.platform.review.repository.StepDownICRepository;
import qraps.platform.review.repository.TransistorRepository;
import qraps.platform.utils.EntityHelper;
import qraps.platform.utils.ParsedExcel;
import qraps.platform.web.controller.dto.ValidateTarget;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DirectReviewService {

    private final ExcelParseService excelParseService;

    @Autowired
    private StepDownICRepository stepdownIcRepository;


    @Autowired
    private TransistorRepository transistorRepository;


    @Autowired
    private DiodeRepository diodeRepository;


    @Autowired
    public DirectReviewService(ExcelParseService excelParseService) {
        this.excelParseService = excelParseService;
    }


    public ResponseReviewDto directReview(ValidateTarget target, MultipartFile file) throws IOException {
        ParsedExcel parsedExcel = excelParseService.parseExcel(file.getInputStream());
        String partNo = parsedExcel.getPartNo();

        Object foundEntity = findTargetEntity(target, partNo);

        List<ReviewDto.Result> reviewResults = reviewPart(parsedExcel.getParsedRows(), foundEntity);
        return ResponseReviewDto.builder()
                .targetName(target.getTarget())
                .reviewResults(reviewResults)
                .passReview(validatePassReview(reviewResults))
                .build();
    }

    private Object findTargetEntity(ValidateTarget target, String partNo) {
        switch (target) {
            case IC:
                return stepdownIcRepository.findById(partNo)
                        .orElseThrow(() -> new RuntimeException("검증 항목이 잘못되었거나 존재하지 않습니다." + partNo));

            case TRANSISTOR:
                return transistorRepository.findById(partNo)
                        .orElseThrow(() -> new RuntimeException("검증 항목이 잘못되었거나 존재하지 않습니다." + partNo));

            case DIODE:
                return diodeRepository.findById(partNo)
                        .orElseThrow(() -> new RuntimeException("검증 항목이 잘못되었거나 존재하지 않습니다." + partNo));

            default:
                throw new RuntimeException("검증 항목이 잘못되었거나 존재하지 않습니다." + partNo);
        }
    }

    // 설계값과 데이터베이스에 저장된 기준 값 검증
    private List<ReviewDto.Result> reviewPart(List<ExcelMapperDto> parsedRows, Object foundEntity) {
        Map<String, Object> referenceValue = EntityHelper.convertEntityToMap(foundEntity);

        return parsedRows.stream()
//                .skip(1) // DB 조회용 partName 제외
                .filter(row -> row.needValidate())
                .map(dto -> {
                    String partName = dto.getPartName();
                    Double designValue = dto.getDesignValue();

                    boolean validateResult = validateDesignValue(partName, referenceValue, designValue);
                    return ReviewDto.Result.builder()
                            .partName(dto.getPartName())
                            .designValue(dto.getDesignValue())
                            .passValidate(validateResult)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private boolean validateDesignValue(String partName, Map<String, Object> referenceValue, Double designValue) {
        /**
         * Todo: check floating point Double -> BigDecimal
         * */
        return Objects.equals(referenceValue.get(partName), designValue);
    }

    // 모든 rows 중 검증값이 하나라도 맞지 않으면 최종 검증 결과 FAIL
    private boolean validatePassReview(List<ReviewDto.Result> reviewResults) {
        return reviewResults.stream().allMatch(ReviewDto.Result::isPassValidate);
    }

}
