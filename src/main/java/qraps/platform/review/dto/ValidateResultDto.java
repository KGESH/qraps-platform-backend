package qraps.platform.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ValidateResultDto {
    // 항목
    private String partName;

    // 검증 결과
    private boolean isValid;

}
