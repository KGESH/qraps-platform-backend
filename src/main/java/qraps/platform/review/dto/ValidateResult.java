package qraps.platform.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateResult {
    // 항목
    private String partNo;

    // 검증 결과
    private boolean verification;

    public ValidateResult() {

    }

}
