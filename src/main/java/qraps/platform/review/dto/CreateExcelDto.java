package qraps.platform.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import qraps.platform.web.controller.dto.ValidateTarget;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class CreateExcelDto {
    String partNo;
    ValidateTarget validateTarget;

    List<ExcelHeaderRowDto> excelHeaderRowFields;
    List<DirectExcelMapperDto> excelRowFields;


    @Getter
    @Setter
    public static class ExcelHeaderRowDto {

        private String partName;

        // 비고
        private int note;

        // 단위
        private Optional<String> unit;

        // 설계 값
        private String designValue;

        // 검증 대상
        private String needValidate;

        public boolean needValidate() {
            return Objects.equals("o", needValidate);
        }
    }
}
