package qraps.platform.review.service;

import org.springframework.stereotype.Service;
import qraps.platform.review.dto.ExcelMapperDto;
import qraps.platform.review.dto.ValidateResultDto;

@Service
public class ValidationService {


    public ValidateResultDto validate(ExcelMapperDto excelRow) {
        /**
         * Todo: validate from db
         * */

        return ValidateResultDto.builder()
                .partName(excelRow.getPartName())
                .isValid(true)
                .build();
    }
}
