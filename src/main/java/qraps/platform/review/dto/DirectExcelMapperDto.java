package qraps.platform.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Builder
public class DirectExcelMapperDto {
    // 항목
    private String partName;

    // 비고
    private int note;

    // 단위
    private Optional<String> unit;

    // 설계 값
    private Number designValue;

    // 검증 대상
    private String needValidate;

    public boolean needValidate() {
        return Objects.equals("o", needValidate);
    }
}
