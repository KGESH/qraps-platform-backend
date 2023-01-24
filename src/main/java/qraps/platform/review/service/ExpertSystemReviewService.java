package qraps.platform.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import qraps.platform.report.service.ReportService;
import qraps.platform.review.dto.RequestExpertSystemDto;
import qraps.platform.review.dto.ResponseReviewDto;
import qraps.platform.web.controller.dto.ReviewPageDto;

@Service
public class ExpertSystemReviewService {

    private final CsvParserService csvParserService;

    private final ReportService reportService;

    @Autowired
    public ExpertSystemReviewService(CsvParserService csvParserService, ReportService reportService) {
        this.csvParserService = csvParserService;
        this.reportService = reportService;
    }


    /**
     * Todo: replace after demo
     */
    private ResponseReviewDto getReviewResultFromExpertSystem(String target, MultipartFile uploadedFile) {
        RequestExpertSystemDto requestBody = RequestExpertSystemDto.builder()
                .target(target)
                .file(uploadedFile)
                .build();

        String url = "http://expert:5001/verify";
        WebClient apiClient = WebClient.builder().baseUrl(url).build();
        return apiClient
                .post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(ResponseReviewDto.class)
                .block();
    }

    /**
     * Expert System에
     * 검증 대상, 검증 대상 partNo
     * Excel 파일 전달
     */
    public ResponseReviewDto reviewFromExpertSystem(ReviewPageDto reviewDto, MultipartFile uploadedFile) {

        String url = "http://expert:5001/expert/review";
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", uploadedFile.getResource());

        WebClient apiClient = WebClient.builder().baseUrl(url).build();
        return apiClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("validTarget", reviewDto.getValidTarget().getTarget())
                        .queryParam("partNo", reviewDto.getPartNo())
                        .build())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(ResponseReviewDto.class)
                .block();
    }

}
