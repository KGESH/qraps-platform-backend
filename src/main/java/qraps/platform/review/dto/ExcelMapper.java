package qraps.platform.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * Demo DTO
 */
@Getter
@Setter
@Builder
public class ExcelMapper {
    // 소자 ID
    @Schema(example = "LM2576HVSX-ADJ/NOPB", required = true)
    private String partNo;

    // 검증 항목
    @Schema(example = "oprating_temperature_max", required = true)
    private String verificationTarget;

    // 비고
    @Schema(example = "not required", required = false)
    private String item;

    // 단위
    @Schema(example = "not required", required = false)
    private String unit;

    // 설계 값
    @Schema(example = "125", required = true)
    private Number designValue;

}
