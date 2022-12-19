package qraps.platform.report.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qraps.platform.report.dto.ReportDto;

import java.nio.file.Path;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class ReportServiceTest {
    private ReportService reportService;

    @BeforeEach
    void init() {
        reportService = new ReportService();
    }

    @Test
    void 리포트_템플릿_컴파일_성공() throws Exception {
        //given
        String mockReportId = "mock-id";
        String mockTarget = "Transistor";
        ReportDto.Generate mockReportDto =
                ReportDto.Generate.builder()
                        .targetName(mockTarget)
                        .reportId(mockReportId)
                        .build();

        //when
        String generatedReportUrl = reportService.generateReport(mockReportDto, new ArrayList<>());

        //then
        Path givenUrl = Path.of("src/main/resources/static/", mockTarget + "/" + mockReportId);
        assertThat(generatedReportUrl).isEqualTo(givenUrl);
    }

}
