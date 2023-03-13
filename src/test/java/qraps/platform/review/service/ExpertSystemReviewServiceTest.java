package qraps.platform.review.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import qraps.platform.review.dto.Criteria;
import qraps.platform.review.dto.ExpertExcelMapperDto;
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
        ExpertExcelMapperDto validateMinRequest = createExpertSystemMockRequest("efficiency_min", 11);
        ExpertExcelMapperDto validateEqualRequest = createExpertSystemMockRequest("efficiency_typ", 44);
        ExpertExcelMapperDto validateMaxRequest = createExpertSystemMockRequest("efficiency_max", 99);
        ExpertExcelMapperDto validateToleranceRequest = createExpertSystemMockRequest("thermal_recovery_typ", 100);

        ValidateResultDto validateMinResult = validationService.validate(validateMinRequest, verificationDto);
        ValidateResultDto validateEqualResult = validationService.validate(validateEqualRequest, verificationDto);
        ValidateResultDto validateMaxResult = validationService.validate(validateMaxRequest, verificationDto);
        ValidateResultDto validateToleranceResult = validationService.validate(validateToleranceRequest, verificationDto);


        //then
        assertThat(validateMinResult.getVerificationTarget()).isEqualTo("efficiency_min");
        assertThat(validateMinResult.isValid()).isEqualTo(true);

        assertThat(validateEqualResult.getVerificationTarget()).isEqualTo("efficiency_typ");
        assertThat(validateEqualResult.isValid()).isEqualTo(true);

        assertThat(validateMaxResult.getVerificationTarget()).isEqualTo("efficiency_max");
        assertThat(validateMaxResult.isValid()).isEqualTo(true);

        assertThat(validateToleranceResult.getVerificationTarget()).isEqualTo("thermal_recovery_typ");
        assertThat(validateToleranceResult.isValid()).isEqualTo(true);

    }

    private ExpertExcelMapperDto createExpertSystemMockRequest(String verificationTarget, Number designValue) {
        return ExpertExcelMapperDto.builder().partNo(mockPartNo).verificationTarget(verificationTarget).designValue(designValue).build();
    }

    private StepDownIC createMockCriteria() {
        return StepDownIC.builder()
                .partNo(criteriaPartNo)
                .efficiency_min((double) Criteria.MIN.getCriteria())
                .efficiency_typ((double) Criteria.RANGE.getCriteria())
                .efficiency_max((double) Criteria.MAX.getCriteria())
                .thermal_recovery_typ(Criteria.TOLERANCE.getCriteria())
                .build();
    }

    private StepDownIC getMockSdic() {
        return StepDownIC.builder()
                .partNo(mockPartNo)
                .efficiency_min(0.0)
                .efficiency_typ(50.0)
                .efficiency_max(100.0)
                .thermal_recovery_typ(100)
                .build();
    }

}
