package qraps.platform.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import qraps.platform.review.dto.Criteria;
import qraps.platform.review.dto.ExcelMapper;
import qraps.platform.review.dto.ReviewDto;
import qraps.platform.review.dto.ValidateResultDto;
import qraps.platform.review.entity.PartList;
import qraps.platform.review.repository.DiodeRepository;
import qraps.platform.review.repository.PartListMapperRepository;
import qraps.platform.review.repository.StepDownICRepository;
import qraps.platform.review.repository.TransistorRepository;
import qraps.platform.utils.EntityHelper;
import qraps.platform.web.controller.dto.ReviewPageDto;
import qraps.platform.web.controller.dto.ValidateTarget;

import java.util.List;
import java.util.Map;
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

    public ReviewDto.Verification getVerificationDto(ReviewPageDto reviewDto) {
        // Todo: 기준 row 조회
        Object criteriaEntity = getCriteriaEntity(reviewDto.getValidTarget());
        Object targetEntity = findEntity(reviewDto);

        // Entity 변수 이름을 key로 사용하는 조회용 Map
        Map<String, Object> criteriaValue = EntityHelper.convertEntityToMap(criteriaEntity);
        Map<String, Object> referenceValue = EntityHelper.convertEntityToMap(targetEntity);

        return ReviewDto.Verification.builder()
                .criteria(criteriaValue)
                .target(referenceValue)
                .build();

    }

    public Object getCriteriaEntity(ValidateTarget validTarget) {
        switch (validTarget) {
            case IC:
                ReviewPageDto sdicDto = ReviewPageDto.builder()
                        .validTarget(validTarget)
                        .partNo("sdic")
                        .build();
                return findEntity(sdicDto);

            case TRANSISTOR:
                ReviewPageDto transistorDto = ReviewPageDto.builder()
                        .validTarget(validTarget)
                        .partNo("bjt")
                        .build();
                return findEntity(transistorDto);

            case DIODE:
                ReviewPageDto diodeDto = ReviewPageDto.builder()
                        .validTarget(validTarget)
                        .partNo("diode")
                        .build();
                return findEntity(diodeDto);

            default:
                throw new RuntimeException("검증 기준 row 조회 error.");
        }

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


    public ValidateResultDto validate(ExcelMapper excelRow, ReviewDto.Verification verificationDto) {

        // 검증 항목 row
        String targetName = excelRow.getVerificationTarget();
        Number designValue = excelRow.getDesignValue();

        boolean isValid = validateDesignValue(targetName, verificationDto, designValue);

        return ValidateResultDto.builder()
                .verificationTarget(excelRow.getVerificationTarget())
                .isValid(isValid)
                .build();

    }

    /**
     * Todo: check floating point Double -> BigDecimal
     */
    /**
     * Criteria
     * 0: N/A
     * 1: Min
     * 2: Equal
     * 3: Max
     * 4: Datasheet 기준 값의 -20% ~ +20% 사이에 설계 값이 포함(경계 포함)되면 pass
     * 5: 별 의미 없음
     */
    public boolean validateDesignValue(String partName, ReviewDto.Verification verificationDto, Number designValue) {

        Map<String, Object> referenceEntity = verificationDto.getTarget();
        Map<String, Object> criteria = verificationDto.getCriteria();
        Number referenceValue = ((Number) referenceEntity.get(partName));


        int criteriaIndex = ((Number) criteria.get(partName)).intValue();
        Criteria criteriaEnum = Criteria.values()[criteriaIndex];

        switch (criteriaEnum) {
            case NA:
                throw new IllegalArgumentException("Unexpected criteria value: " + criteriaIndex);

            case MIN:
                return verifyMinValue(designValue, referenceValue);

            case MAX:
                return verifyMaxValue(designValue, referenceValue);

            case EQUAL:
                return verifyEqualValue(designValue, referenceValue);

            case TOLERANCE:
                return verifyToleranceValue(designValue, referenceValue);

            case RESERVED:
                throw new IllegalArgumentException("Unexpected criteria value: " + criteriaIndex);

            default:
                throw new IllegalArgumentException("Unexpected criteria value: " + criteriaIndex);

        }

    }

    private boolean verifyMinValue(Number designValue, Number referenceValue) {
        return designValue.doubleValue() <= referenceValue.doubleValue();
    }

    private boolean verifyMaxValue(Number designValue, Number referenceValue) {
        return designValue.doubleValue() >= referenceValue.doubleValue();
    }

    private boolean verifyEqualValue(Number designValue, Number referenceValue) {
        return designValue.doubleValue() == referenceValue.doubleValue();
    }

    /**
     * 설계 값이 오차 범위 이내: true,
     * 설계 값이 오차 범위 벗어남: false
     */
    private boolean verifyToleranceValue(Number designValue, Number referenceValue) {
        double lowerBound = referenceValue.doubleValue() * (1 - Criteria.TOLERANCE.getToleranceMinRate());
        double upperBound = referenceValue.doubleValue() * (1 + Criteria.TOLERANCE.getToleranceMaxRate());

        return lowerBound <= designValue.doubleValue() && designValue.doubleValue() <= upperBound;
    }

}
