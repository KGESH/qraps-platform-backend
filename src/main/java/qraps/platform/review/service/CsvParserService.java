package qraps.platform.review.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.review.dto.ReviewDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CsvParserService {

    public List<ReviewDto.CsvPositionMapper> parseByPosition(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String[] headers = reader.readLine().split(",");
        List dtos = new CsvToBeanBuilder(reader)
                .withType(ReviewDto.CsvPositionMapper.class)
                .build()
                .parse();

        reader.close();
        return dtos;
    }

    public List<ReviewDto.CsvNameMapper> parseByColumnName(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        List dtos = new CsvToBeanBuilder(reader)
                .withType(ReviewDto.CsvNameMapper.class)
                .build()
                .parse();

        reader.close();
        return dtos;
    }
}
