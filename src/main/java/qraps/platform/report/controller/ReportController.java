package qraps.platform.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.report.service.ReportResultService;

import java.io.IOException;


@Controller
public class ReportController {
    private final ReportResultService reportResultService;

    @Autowired
    public ReportController(ReportResultService reportResultService) {
        this.reportResultService = reportResultService;
    }

    /**
     * Todo: handle global exception
     */
    @PostMapping("report")
    public ResponseEntity<byte[]> printReport(
            @RequestParam("reportUrl") String reportUrl,
            @RequestParam(value = "file", required = false) MultipartFile reportCoverPage) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.pdf\"");

        if (reportCoverPage.isEmpty()) {
            byte[] report = reportResultService.getReport(reportUrl);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(report);
        }

        byte[] reportWithCoverPage = reportResultService.getReportWithCoverPage(reportUrl, reportCoverPage);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(reportWithCoverPage);
    }

}
