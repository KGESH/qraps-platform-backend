package qraps.platform.review.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import qraps.platform.review.dto.Criteria;
import qraps.platform.review.dto.ExcelMapper;
import qraps.platform.review.dto.ReviewDto;
import qraps.platform.review.dto.ValidateResultDto;
import qraps.platform.review.entity.StepDownIC;
import qraps.platform.review.repository.StepDownICRepository;
import qraps.platform.web.controller.dto.ReviewPageDto;
import qraps.platform.web.controller.dto.ValidateTarget;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ExpertSystemReviewServiceTest {

    @InjectMocks
    ValidationService validationService;
    @Mock
    StepDownICRepository stepDownICRepository;

    String criteriaPartNo = "sdic";
    String mockPartNo = "mock-part-no";

    @Test
    void 전문가_시스템_검증_요청_성공() {

        //given
        ReviewPageDto reviewPageDto = ReviewPageDto.builder().partNo(mockPartNo).validTarget(ValidateTarget.IC).build();
        //Mock
        given(stepDownICRepository.findById(criteriaPartNo))
                .willReturn(Optional.ofNullable(createMockCriteria()));
        given(stepDownICRepository.findById(mockPartNo))
                .willReturn(Optional.ofNullable(getMockSdic()));


        //when
        ReviewDto.Verification verificationDto = validationService.getVerificationDto(reviewPageDto);
        ExcelMapper validateMinRequest = createExpertSystemMockRequest("oprating_temperature_min", 1);
        ExcelMapper validateEqualRequest = createExpertSystemMockRequest("efficiency_typ", 100);
        ExcelMapper validateMaxRequest = createExpertSystemMockRequest("oprating_temperature_max", 9999);
        ExcelMapper validateToleranceRequest = createExpertSystemMockRequest("thermal_recovery_typ", 100);

        ValidateResultDto validateMinResult = validationService.validate(validateMinRequest, verificationDto);
        ValidateResultDto validateEqualResult = validationService.validate(validateEqualRequest, verificationDto);
        ValidateResultDto validateMaxResult = validationService.validate(validateMaxRequest, verificationDto);
        ValidateResultDto validateToleranceResult = validationService.validate(validateToleranceRequest, verificationDto);


        //then
        assertThat(validateMinResult.getVerificationTarget()).isEqualTo("oprating_temperature_min");
        assertThat(validateMinResult.isValid()).isEqualTo(true);

        assertThat(validateEqualResult.getVerificationTarget()).isEqualTo("efficiency_typ");
        assertThat(validateEqualResult.isValid()).isEqualTo(true);

        assertThat(validateMaxResult.getVerificationTarget()).isEqualTo("oprating_temperature_max");
        assertThat(validateMaxResult.isValid()).isEqualTo(true);

        assertThat(validateToleranceResult.getVerificationTarget()).isEqualTo("thermal_recovery_typ");
        assertThat(validateToleranceResult.isValid()).isEqualTo(true);

    }

    private ExcelMapper createExpertSystemMockRequest(String verificationTarget, Number designValue) {
        return ExcelMapper.builder().partNo(mockPartNo).verificationTarget(verificationTarget).designValue(designValue).build();
    }

    private StepDownIC createMockCriteria() {
        return StepDownIC.builder().partNo(criteriaPartNo)
                .oprating_temperature_min(Criteria.MIN.getCriteria())
                .efficiency_typ((double) Criteria.EQUAL.getCriteria())
                .oprating_temperature_max(Criteria.MAX.getCriteria())
                .thermal_recovery_typ(Criteria.TOLERANCE.getCriteria())
                .build();
    }

    private StepDownIC getMockSdic() {
        return StepDownIC.builder().partNo(mockPartNo)
                .oprating_temperature_min(5)
                .efficiency_typ(100.0)
                .oprating_temperature_max(10)
                .thermal_recovery_typ(100)
                .build();
    }

}
