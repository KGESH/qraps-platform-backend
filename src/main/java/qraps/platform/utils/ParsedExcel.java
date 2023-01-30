package qraps.platform.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import qraps.platform.review.dto.DirectExcelMapperDto;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ParsedExcel {
    private String partNo;
    private List<DirectExcelMapperDto> parsedRows;
}
