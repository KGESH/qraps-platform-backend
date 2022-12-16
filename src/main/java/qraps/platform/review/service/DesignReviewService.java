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
public class DesignReviewService {

    /** Todo: 검증 대상 분기 */
    public String designReview(String target, MultipartFile file) {
        try {
            parseByColumnName(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    private List<ReviewDto.PositionMapper> parseByPosition(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String[] headers = reader.readLine().split(",");
        List dtos = new CsvToBeanBuilder(reader)
                .withType(ReviewDto.PositionMapper.class)
                .build()
                .parse();

        reader.close();
        return dtos;
    }

    private List<ReviewDto.NameMapper> parseByColumnName(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        List dtos = new CsvToBeanBuilder(reader)
                .withType(ReviewDto.NameMapper.class)
                .build()
                .parse();

        reader.close();
        return dtos;
    }
}
