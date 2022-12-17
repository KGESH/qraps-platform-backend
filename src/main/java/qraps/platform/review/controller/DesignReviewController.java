package qraps.platform.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.review.service.DesignReviewService;

@Controller
public class DesignReviewController {

    private final DesignReviewService designReviewService;

    @Autowired
    public DesignReviewController(DesignReviewService designReviewService) {
        this.designReviewService = designReviewService;
    }

    @GetMapping()
    @ResponseBody()
    public String healthCheckToInferenceEngine() {
        return designReviewService.healthCheckToInferenceEngine();
    }

    @PostMapping("review")
    public String designReview(@RequestParam("target") String target, @RequestParam("file") MultipartFile csvFile) {
        designReviewService.designReview(target, csvFile);

        return "redirect:/";
    }
}
