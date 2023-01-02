package qraps.platform.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.report.service.ReportService;
import qraps.platform.review.dto.ResponseExpertSystem;
import qraps.platform.review.dto.ReviewDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpertSystemReviewService {

    private final CsvParserService csvParserService;

    private final ReportService reportService;

    @Autowired
    public ExpertSystemReviewService(CsvParserService csvParserService, ReportService reportService) {
        this.csvParserService = csvParserService;
        this.reportService = reportService;
    }

    public ResponseExpertSystem.Result reviewFromExpertSystem(String target, MultipartFile uploadedFile) {
        ResponseExpertSystem.Result response = getReviewResultFromExpertSystem(target, uploadedFile);
        return response;
    }

    /**
     * 전문가 시스템에 검증 요청
     * 검증 대상과 엑셀 파일 전달
     */
    private ResponseExpertSystem.Result getReviewResultFromExpertSystem(String target, MultipartFile uploadedFile) {
        List<ReviewDto.Result> mockReviewResults = getMockReviewResults();

        return ResponseExpertSystem.Result.builder()
                .targetName(target)
                .reviewResults(mockReviewResults)
                .isPass(true)
                .build();
    }

    /**
     * Todo: replace after demo
     */
//    private ResponseExpertSystem.Result getReviewResultFromExpertSystem(String target, MultipartFile uploadedFile) {
//        ReviewDto.RequestExpertSystem requestBody = ReviewDto.RequestExpertSystem.builder()
//                .target(target)
//                .file(uploadedFile)
//                .build();
//
//        String url = "http://0.0.0.0:5000/verify";
//        WebClient apiClient = WebClient.builder().baseUrl(url).build();
//        return apiClient
//                .post()
//                .uri(url)
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(ResponseExpertSystem.Result.class)
//                .block();
//    }
    private List<ReviewDto.Result> getMockReviewResults() {
        List<ReviewDto.Result> mockReviewResults = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            mockReviewResults.add(
                    ReviewDto.Result.builder()
                            .propertyName("property" + i)
                            .value("value" + i)
                            .passValidate(true)
                            .build());

        }

        return mockReviewResults;
    }

}
