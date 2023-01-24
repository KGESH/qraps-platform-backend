package qraps.platform.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import qraps.platform.review.dto.ExcelMapper;
import qraps.platform.review.dto.ValidateResultDto;
import qraps.platform.review.entity.PartList;
import qraps.platform.review.repository.DiodeRepository;
import qraps.platform.review.repository.PartListMapperRepository;
import qraps.platform.review.repository.StepDownICRepository;
import qraps.platform.review.repository.TransistorRepository;
import qraps.platform.web.controller.dto.ReviewPageDto;
import qraps.platform.web.controller.dto.ValidateTarget;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ValidationService {
    @Autowired
    private PartListMapperRepository partListMapperRepository;
    @Autowired
    private StepDownICRepository stepDownICRepository;
    @Autowired
    private DiodeRepository diodeRepository;
    @Autowired
    private TransistorRepository transistorRepository;


    /**
     * 소자 맵핑 테이블의 partNo 컬럼을 기준으로 정렬
     */
    public List<PartList> findAllPartListOrderByPartNo(ValidateTarget validateTarget) {
        return partListMapperRepository.findAll(Sort.by(Sort.Direction.ASC, "partNo"))
                .stream()
                .filter(item -> item.getDevice() == validateTarget.getTableIndex())
                .collect(Collectors.toList());
    }


    public Object findEntity(ReviewPageDto reviewDto) {
        String partNo = reviewDto.getPartNo();
        ValidateTarget target = reviewDto.getValidTarget();

        switch (target) {
            case IC:
                return stepDownICRepository.findById(partNo).orElseThrow(() -> new RuntimeException("IC 검증 대상의 partNo가 데이터베이스에 존재하지 않습니다."));

            case TRANSISTOR:
                return transistorRepository.findById(partNo).orElseThrow(() -> new RuntimeException("TRANSISTOR 검증 대상의 partNo가 데이터베이스에 존재하지 않습니다."));

            case DIODE:
                return diodeRepository.findById(partNo).orElseThrow(() -> new RuntimeException("DIODE 검증 대상의 partNo가 데이터베이스에 존재하지 않습니다."));

            default:
                throw new RuntimeException("Review Page DTO Error");
        }
    }

    public ReviewPageDto findTargetByPartNo(String partNo) {
        PartList partList = partListMapperRepository.findByPartNo(partNo).orElseThrow(() -> new RuntimeException("partNo를 찾을 수 없습니다."));
        int tableIndex = partList.getDevice();

        // Todo: refactor to enum
        switch (tableIndex) {
            case 1:
                return ReviewPageDto.builder()
                        .validTarget(ValidateTarget.IC)
                        .partNo(partNo)
                        .build();
            case 2:
                return ReviewPageDto.builder()
                        .validTarget(ValidateTarget.TRANSISTOR)
                        .partNo(partNo)
                        .build();
            case 3:
                return ReviewPageDto.builder()
                        .validTarget(ValidateTarget.DIODE)
                        .partNo(partNo)
                        .build();
            default:
                throw new RuntimeException("PartNo의 데이터베이스 테이블 index가 잘못되었습니다.");
        }
    }


    public ValidateResultDto validate(ExcelMapper excelRow, Map<String, Object> referenceValue) {

        // 검증 항목 row
        String targetName = excelRow.getVerificationTarget();
        Double designValue = excelRow.getDesignValue();

        boolean isValid = Objects.equals(referenceValue.get(targetName), designValue);
        return ValidateResultDto.builder()
                .partName(excelRow.getPartNo())
                .isValid(isValid)
                .build();
    }
}
