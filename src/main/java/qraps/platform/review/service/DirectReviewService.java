package qraps.platform.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.report.dto.ReportDto;
import qraps.platform.report.service.ReportService;
import qraps.platform.review.dto.ReviewDto;
import qraps.platform.review.entity.DevMockEntity;
import qraps.platform.review.repository.DevMockEntityRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DirectReviewService {

    private final CsvParserService csvParserService;
    private final ReportService reportService;

    @Autowired
    private DevMockEntityRepository devMockEntityRepository;


    @Autowired
    public DirectReviewService(CsvParserService csvParserService, ReportService reportService) {
        this.csvParserService = csvParserService;
        this.reportService = reportService;
    }


    /**
     * Todo: 검증 대상 분기
     */
    public String directReview(String target, MultipartFile file) throws Exception {

        List<ReviewDto.CsvPositionMapper> parsedDtos = csvParserService.parseByPosition(file);

        /** Todo: DB 조회 후 DTO 검증 */
        List<ReviewDto.Result> results =
                parsedDtos.stream()
                        .map(dto -> {
                            String name = dto.getName();

                            DevMockEntity foundEntity = devMockEntityRepository.findByName(name)
                                    .orElseThrow(() -> new RuntimeException("CSV 속성 이름이 데이터베이스에 존재하지 않습니다."));

                            return foundEntity.review(dto);
                        }).collect(Collectors.toList());

        /** Todo: 검증 결과 저장 */
        String mockSavedResultId = UUID.randomUUID().toString();


        ReportDto.Generate generateReportDto =
                ReportDto.Generate.builder()
                        .targetName(target)
                        .reportId(mockSavedResultId)
                        .build();

        String generatedReportUrl = reportService.generateReport(generateReportDto, results);

        /** Todo: 검증 결과 ID 반환 */
        return generatedReportUrl;
    }
}
