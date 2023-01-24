package qraps.platform.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import qraps.platform.review.dto.ExcelMapperDto;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ParsedExcel {
    private String partNo;
    private List<ExcelMapperDto> parsedRows;
}
