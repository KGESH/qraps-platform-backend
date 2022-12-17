package qraps.platform.review.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import qraps.platform.review.dto.ReviewDto;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static qraps.platform.review.dto.ReviewDto.*;

@Service
public class DesignReviewService {


    /**
     * Todo: 검증 대상 분기
     */
    public String designReview(String target, MultipartFile file) {
        try {
            parseByColumnName(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    public String healthCheckToInferenceEngine() {
        String url = "http://localhost:7777/resource";

        String response = WebClient.create()
                .post()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }


    private List<CsvPositionMapper> parseByPosition(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String[] headers = reader.readLine().split(",");
        List dtos = new CsvToBeanBuilder(reader)
                .withType(CsvPositionMapper.class)
                .build()
                .parse();

        reader.close();
        return dtos;
    }

    private List<CsvNameMapper> parseByColumnName(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        List dtos = new CsvToBeanBuilder(reader)
                .withType(CsvNameMapper.class)
                .build()
                .parse();

        reader.close();
        return dtos;
    }
}
