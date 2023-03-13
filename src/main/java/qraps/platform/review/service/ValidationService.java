package qraps.platform.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import qraps.platform.global.error.exception.BusinessException;
import qraps.platform.global.error.exception.EntityNotFoundException;
import qraps.platform.global.error.exception.ErrorCode;
import qraps.platform.review.dto.Criteria;
import qraps.platform.review.dto.ExpertExcelMapperDto;
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
import java.util.Objects;
import java.util.Optional;
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
        return partListMapperRepository.findAll(Sort.by(Sort.Direction.ASC, "partNo")).stream().filter(item -> item.getDevice() == validateTarget.getTableIndex()).collect(Collectors.toList());
    }

    public ReviewDto.Verification getVerificationDto(ReviewPageDto reviewDto) {
        // Todo: 기준 row 조회
        Object criteriaEntity = getCriteriaEntity(reviewDto.getValidTarget());
        Object targetEntity = findEntity(reviewDto);

        // Entity 변수 이름을 key로 사용하는 조회용 Map
        Map<String, Object> criteriaValue = EntityHelper.convertEntityToMap(criteriaEntity);
        Map<String, Object> referenceValue = EntityHelper.convertEntityToMap(targetEntity);

        return ReviewDto.Verification.builder().criteria(criteriaValue).target(referenceValue).build();

    }

    public Object getCriteriaEntity(ValidateTarget validTarget) {
        switch (validTarget) {
            case IC:
                ReviewPageDto sdicDto = ReviewPageDto.builder().validTarget(validTarget).partNo("sdic").build();
                return findEntity(sdicDto);

            case TRANSISTOR:
                ReviewPageDto transistorDto = ReviewPageDto.builder().validTarget(validTarget).partNo("bjt").build();
                return findEntity(transistorDto);

            case DIODE:
                ReviewPageDto diodeDto = ReviewPageDto.builder().validTarget(validTarget).partNo("diode").build();
                return findEntity(diodeDto);

            default:
                throw new EntityNotFoundException("검증 Criteria row 조회 실패.");
        }

    }


    public Object findEntity(ReviewPageDto reviewDto) {
        String partNo = reviewDto.getPartNo();
        ValidateTarget target = reviewDto.getValidTarget();

        switch (target) {
            case IC:
                return stepDownICRepository.findById(partNo).orElseThrow(() -> new EntityNotFoundException("IC 검증 대상의 partNo가 데이터베이스에 존재하지 않습니다."));

            case TRANSISTOR:
                return transistorRepository.findById(partNo).orElseThrow(() -> new EntityNotFoundException("TRANSISTOR 검증 대상의 partNo가 데이터베이스에 존재하지 않습니다."));

            case DIODE:
                return diodeRepository.findById(partNo).orElseThrow(() -> new EntityNotFoundException("DIODE 검증 대상의 partNo가 데이터베이스에 존재하지 않습니다."));

            default:
                throw new EntityNotFoundException("Review Page DTO Error");
        }
    }

    public ReviewPageDto findTargetByPartNo(String partNo) {
        PartList partList = partListMapperRepository.findByPartNo(partNo).orElseThrow(() -> new EntityNotFoundException("partNo를 찾을 수 없습니다."));
        int tableIndex = partList.getDevice();

        // Todo: refactor to enum
        switch (tableIndex) {
            case 1:
                return ReviewPageDto.builder().validTarget(ValidateTarget.IC).partNo(partNo).build();
            case 2:
                return ReviewPageDto.builder().validTarget(ValidateTarget.TRANSISTOR).partNo(partNo).build();
            case 3:
                return ReviewPageDto.builder().validTarget(ValidateTarget.DIODE).partNo(partNo).build();
            default:
                throw new EntityNotFoundException("PartNo의 데이터베이스 테이블 index가 잘못되었습니다.");
        }
    }

    public ValidateResultDto validate(ExpertExcelMapperDto excelRow, ReviewDto.Verification verificationDto) {

        // 검증 항목 row
        String targetName = excelRow.getVerificationTarget();
        Object designValue = excelRow.getDesignValue();

        boolean isValid = validateDesignValue(targetName, verificationDto, designValue);

        return ValidateResultDto.builder().verificationTarget(excelRow.getVerificationTarget()).isValid(isValid).build();

    }

    /**
     * Todo: check floating point Double -> BigDecimal
     */
    /**
     * Criteria
     * 0: N/A
     * 1: Min
     * 2: Range 범위에 설계값이 포함되거나(경계 포함), DB의 typ 컬럼의 값과 일치하면 pass
     * 3: Max
     * 4: Datasheet 기준 값의 -20% ~ +20% 사이에 설계 값이 포함(경계 포함)되면 pass
     * 5: 별 의미 없음
     */
    public boolean validateDesignValue(String partName, ReviewDto.Verification verificationDto, Object designValue) {

        Map<String, Object> referenceEntity = verificationDto.getTarget();
        Map<String, Object> criteria = verificationDto.getCriteria();
        Optional<Object> referenceOptional = Optional.ofNullable(referenceEntity.get(partName));


        int criteriaIndex;
        Object criteriaValue = criteria.get(partName);
        if (criteriaValue instanceof String) {
            criteriaIndex = Integer.parseInt((String) criteriaValue);
        } else if (criteriaValue instanceof Number) {
            criteriaIndex = ((Number) criteriaValue).intValue();
        } else {
            throw new BusinessException("Value must be a string or a number.", ErrorCode.INVALID_TYPE_VALUE);
        }


        Criteria criteriaEnum = Criteria.values()[criteriaIndex];
        switch (criteriaEnum) {
            case NA: /** DB 조회한 값이 null이면 true 반환 */
                return referenceOptional.map(referenceValue -> verifyEqualValue(designValue, referenceValue))
                        .orElse(true);
//                throw new BusinessException("Unexpected criteria value: " + criteriaIndex, ErrorCode.INVALID_INPUT_VALUE);

            case MIN:
//                        verifyMinValue(designValue, referenceOptional
                return referenceOptional.map(referenceValue -> verifyMinValue(Double.parseDouble(designValue.toString()), ((Number) referenceValue))).orElse(true);
//                        .orElseThrow(() -> new EntityNotFoundException("DB에 저장된 해당 필드가 NULL이거나 존재하지 않습니다.\n" + "필드: " + partName + "기준: " + Criteria.MIN)));

            case RANGE: /** 요구사항 확정까지  무조건 pass 처리 */
                return true;
//                return referenceOptional.map(referenceValue -> verifyEqualValue(designValue, referenceValue)).orElse(true);
//                        verifyEqualValue(designValue,
//                        referenceOptional.orElseThrow(() -> new EntityNotFoundException("DB에 저장된 해당 필드가 NULL이거나 존재하지 않습니다.\n" + "필드: " + partName + "기준: " + Criteria.RANGE)));
//                return verifyRangeValue(partName, designValue, referenceEntity, referenceOptional);

            case MAX:
                return referenceOptional.map(referenceValue -> verifyMaxValue(Double.parseDouble(designValue.toString()), ((Number) referenceValue))).orElse(true);
//                return verifyMaxValue(designValue, referenceOptional
//                        .orElseThrow(() -> new EntityNotFoundException("DB에 저장된 해당 필드가 NULL이거나 존재하지 않습니다.\n" + "필드: " + partName + "기준: " + Criteria.MAX)));

            /**
             * 요구사항 확정까지  무조건 pass 처리
             * DB 조회한 값이 null이면 true 반환
             * */
            case TOLERANCE:
                return referenceOptional.map(referenceValue -> verifyToleranceValue(Double.parseDouble(designValue.toString()), ((Number) referenceValue))).orElse(true);

            case RESERVED:
                throw new BusinessException("Unexpected criteria value: " + criteriaIndex, ErrorCode.INVALID_INPUT_VALUE);

            default:
                throw new BusinessException("Unexpected criteria value: DEFAULT CASE", ErrorCode.INVALID_INPUT_VALUE);

        }

    }


    /**
     * 설계값이 기준 min값보다 커야함
     */
    private boolean verifyMinValue(Number designValue, Number referenceValue) {
        return designValue.doubleValue() >= referenceValue.doubleValue();
    }

    /**
     * 설계값이 기준 max값보다 작아야함
     */
    private boolean verifyMaxValue(Number designValue, Number referenceValue) {
        return designValue.doubleValue() <= referenceValue.doubleValue();
    }

    /**
     * 설계값이 기준 범위 값 사이에 있거나
     * 기준 값과 같아야 함
     */
    private boolean verifyRangeValue(String partName, Number designValue, Map<String, Object> referenceEntity, Number referenceValue) {
        String beginColumnName = partName.replace("typ", "min");
        String endColumnName = partName.replace("typ", "max");
        if (isRangeColumnExist(referenceEntity, beginColumnName, endColumnName)) {
            return verifyRange(referenceEntity, designValue, beginColumnName, endColumnName);
        }

        return verifyEqualValue(designValue, referenceValue);
    }

    private boolean verifyRange(Map<String, Object> referenceEntity, Number designValue, String beginColumnName, String endColumnName) {
        Number beginValue = (Number) referenceEntity.get(beginColumnName);
        Number endValue = (Number) referenceEntity.get(endColumnName);

        return (beginValue.doubleValue() <= designValue.doubleValue()) && (designValue.doubleValue() <= endValue.doubleValue());
    }

    private boolean isRangeColumnExist(Map<String, Object> referenceEntity, String beginColumnName, String endColumnName) {
        return referenceEntity.get(beginColumnName) != null && referenceEntity.get(endColumnName) != null;
    }

    private boolean verifyEqualValue(Object designValue, Object referenceValue) {

        if (designValue instanceof Number) {
            return ((Number) designValue).doubleValue() == ((Number) referenceValue).doubleValue();

        } else {
            return Objects.equals(designValue, referenceValue);
        }
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
