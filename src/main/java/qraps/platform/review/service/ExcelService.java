package qraps.platform.review.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import qraps.platform.global.error.exception.BusinessException;
import qraps.platform.global.error.exception.ErrorCode;
import qraps.platform.review.dto.CreateExcelDto.ExcelHeaderRowDto;
import qraps.platform.review.dto.DirectExcelMapperDto;
import qraps.platform.review.dto.ExcelColumn;
import qraps.platform.utils.ParsedExcel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class ExcelService {

    public ParsedExcel parseExcel(InputStream excelStream) throws IOException {
        ArrayList<DirectExcelMapperDto> parsedRows = new ArrayList<>();
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

            Optional<Number> designValue = Optional.of(row.getCell(ExcelColumn.DESIGN_VALUE.getIndex()).getNumericCellValue());
            String needValidate = row.getCell(ExcelColumn.NEED_VALIDATE.getIndex()).getStringCellValue();


            parsedRows.add(
                    DirectExcelMapperDto.builder()
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


    public ByteArrayOutputStream createExcelFile(List<ExcelHeaderRowDto> excelHeaderRows, List<DirectExcelMapperDto> excelRowFields) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("sdic");

        // Create a Font for styling header cells
//        Font headerFont = workbook.createFont();
//        headerFont.setBold(true);
//        headerFont.setFontHeightInPoints((short) 12);

        // Create a CellStyle for styling header cells
//        CellStyle headerCellStyle = workbook.createCellStyle();
//        headerCellStyle.setFont(headerFont);
        int rowNum = 0;
        // Create a Row
        Row headerRow = sheet.createRow(rowNum++);

        // Create header cells
        Cell cell;
        cell = headerRow.createCell(0);
        cell.setCellValue("검증 항목");

        cell = headerRow.createCell(1);
        cell.setCellValue("비고");

        cell = headerRow.createCell(2);
        cell.setCellValue("단위");

        cell = headerRow.createCell(3);
        cell.setCellValue("설계 값");

        cell = headerRow.createCell(4);
        cell.setCellValue("적용 여부");

        for (ExcelHeaderRowDto headerDto : excelHeaderRows) {
            Row row = sheet.createRow(rowNum++);

            // 검증 항목
            cell = row.createCell(0);
            cell.setCellValue(headerDto.getPartName());

            // 비고
            cell = row.createCell(1);
            cell.setCellValue(headerDto.getNote());

            // 단위
            cell = row.createCell(2);
            cell.setCellValue(headerDto.getUnit().orElse(""));

            // 설계 값
            cell = row.createCell(3);
            cell.setCellValue(headerDto.getDesignValue());

            // 적용 여부
            cell = row.createCell(4);
            cell.setCellValue(headerDto.getNeedValidate());

        }

        // set the cell style to an integer format
//        CellStyle intgerCellStyle = workbook.createCellStyle();
//        intgerCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0"));


        // Write data rows
        for (DirectExcelMapperDto dto : excelRowFields) {


            Row row = sheet.createRow(rowNum++);

            // 검증 항목
            cell = row.createCell(0);
            cell.setCellValue(dto.getPartName());

            // 비고
            cell = row.createCell(1);
            cell.setCellValue(dto.getNote());

            // 단위
            cell = row.createCell(2);
            cell.setCellValue(dto.getUnit().orElse(""));

            // 설계 값
            cell = row.createCell(3);
            if (dto.getDesignValue().isPresent()) {
                if (dto.getDesignValue().get() instanceof Double) {
                    cell.setCellValue(dto.getDesignValue().get().doubleValue());

                } else { // instanceof Integer or String
                    cell.setCellValue(dto.getDesignValue().get().toString());
                }

            } else {
                cell.setCellValue("");
            }

            // 적용 여부
            cell = row.createCell(4);
            cell.setCellValue(dto.getNeedValidate());
        }


        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }


        // Write the output to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream;
    }

}
