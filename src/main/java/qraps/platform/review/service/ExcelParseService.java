package qraps.platform.review.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import qraps.platform.global.error.exception.BusinessException;
import qraps.platform.global.error.exception.ErrorCode;
import qraps.platform.review.dto.ExcelColumn;
import qraps.platform.review.dto.ExcelMapperDto;
import qraps.platform.utils.ParsedExcel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;


@Service
public class ExcelParseService {

    public ParsedExcel parseExcel(InputStream excelStream) throws IOException {
        ArrayList<ExcelMapperDto> parsedRows = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(excelStream);

        // 엑셀 파일의 첫번째 시트
        Sheet sheet = workbook.getSheetAt(0);

        // header partNo
        Row headerRow = sheet.getRow(1);
        String partNo = Optional.ofNullable(headerRow.getCell(ExcelColumn.DESIGN_VALUE.getIndex()).getStringCellValue())
                .orElseThrow(() -> new BusinessException("partNo 항목의 설계값이 누락되었습니다.", ErrorCode.INVALID_INPUT_VALUE));


        // header 제외
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            // 엑셀 빈 row 제외
            if (row == null) {
                continue;
            }


            // 적용여부 체크 되어있는 항목만 객체로 맵핑
            if (!needValidateRow(row)) {
                continue;
            }


            String partName = row.getCell(ExcelColumn.PART_NAME.getIndex()).getStringCellValue();
            int note = (int) row.getCell(ExcelColumn.NOTE.getIndex()).getNumericCellValue();
            Optional<String> unit = Optional.ofNullable(row.getCell(ExcelColumn.UNIT.getIndex())).map(Cell::getStringCellValue);

            if (row.getCell(ExcelColumn.DESIGN_VALUE.getIndex()).getCellType() == Cell.CELL_TYPE_BLANK) {
                throw new BusinessException("설계값이 비어있습니다. 검증 대상: " + partName, ErrorCode.INVALID_INPUT_VALUE);
            }

            Number designValue = row.getCell(ExcelColumn.DESIGN_VALUE.getIndex()).getNumericCellValue();
            String needValidate = row.getCell(ExcelColumn.NEED_VALIDATE.getIndex()).getStringCellValue();


            parsedRows.add(
                    ExcelMapperDto.builder()
                            .partName(partName)
                            .note(note)
                            .unit(unit)
                            .designValue(designValue)
                            .needValidate(needValidate)
                            .build());


        }


        return ParsedExcel.builder()
                .partNo(partNo)
                .parsedRows(parsedRows)
                .build();
    }

    /**
     * 적용여부가 체크되어있는 row만 검증 대상에 추가
     */
    private boolean needValidateRow(Row row) {
        Cell needValidateCell = row.getCell(ExcelColumn.NEED_VALIDATE.getIndex());
        return needValidateCell != null && Objects.equals("o", needValidateCell.getStringCellValue());
    }
}
