package qraps.platform.review.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Todo: mapping excel column
 */
@Getter
@Setter
public class ExcelMapperDto {
    // 항목
    private String partName;

    // 비고
    private String note;

    // 단위
    private String unit;

    // 설계 값
    private double designValue;

}
