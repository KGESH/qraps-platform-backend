package qraps.platform.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class InferenceReviewService {

    private final CsvParserService csvParserService;

    @Autowired
    public InferenceReviewService(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    /**
     * Todo: request to engine
     */
    public void reviewFromInferenceEngine(String target, MultipartFile csvFile) {
        return;
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


}
