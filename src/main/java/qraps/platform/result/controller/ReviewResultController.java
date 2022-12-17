package qraps.platform.result.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.result.service.ReviewResultService;


@Controller
public class ReviewResultController {
    private final ReviewResultService reviewResultService;

    @Autowired
    public ReviewResultController(ReviewResultService reviewResultService) {
        this.reviewResultService = reviewResultService;
    }

    /**
     * Todo: handle global exception
     */
    @PostMapping("result")
    public ResponseEntity<byte[]> printReport(@RequestParam("file") MultipartFile jasperReportTemplateFile) throws Exception {
        return reviewResultService.generateReport(jasperReportTemplateFile);
    }

}
