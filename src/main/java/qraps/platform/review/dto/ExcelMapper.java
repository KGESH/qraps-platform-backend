package qraps.platform.review.dto;

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
    private String partNo;

    // 검증 항목
    private String verificationTarget;

    // 비고
    private String item;

    // 단위
    private String unit;

    // 설계 값
    private double designValue;


}
