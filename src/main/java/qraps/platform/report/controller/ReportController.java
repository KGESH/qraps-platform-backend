package qraps.platform.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import qraps.platform.report.service.ReportService;
import qraps.platform.review.dto.ResponseReviewDto;


@Controller
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    /**
     * Todo: handle global exception
     */
    @PostMapping("report")
    public ResponseEntity<byte[]> generateReport(@SessionAttribute("reviewResult") ResponseReviewDto reviewResult) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        String fileName = reviewResult.getTargetName() + "_report.pdf";
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        byte[] report = reportService.generateReport(reviewResult);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(report);
    }

}
