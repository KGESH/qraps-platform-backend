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

    public ResponseReviewDto reviewFromExpertSystem(ReviewPageDto reviewDto, MultipartFile uploadedFile) {
        ResponseReviewDto response = reviewFromExpertSystem(uploadedFile);
        return response;
    }

    /**
     * 전문가 시스템에 검증 요청
     * 검증 대상과 엑셀 파일 전달
     */
//    private ResponseExpertSystem.Result getReviewResultFromExpertSystem(String target, MultipartFile uploadedFile) {
//        List<ReviewDto.Result> mockReviewResults = getMockReviewResults();
//
//        return ResponseExpertSystem.Result.builder()
//                .targetName(target)
//                .reviewResults(mockReviewResults)
//                .isPass(true)
//                .build();
//    }

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
     * Expert System에 Excel 파일 전달
     */
    private ResponseReviewDto reviewFromExpertSystem(MultipartFile uploadedFile) {
        String url = "http://expert:5001/expert/review";
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", uploadedFile.getResource());

        WebClient apiClient = WebClient.builder().baseUrl(url).build();
        return apiClient
                .post()
                .uri(url)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(ResponseReviewDto.class)
                .block();
    }

}
