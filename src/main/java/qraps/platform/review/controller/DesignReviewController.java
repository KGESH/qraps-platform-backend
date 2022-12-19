package qraps.platform.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qraps.platform.review.service.DirectReviewService;
import qraps.platform.review.service.InferenceReviewService;

@Controller
public class DesignReviewController {

    private final InferenceReviewService inferenceReviewService;
    private final DirectReviewService designReviewService;

    @Autowired
    public DesignReviewController(InferenceReviewService inferenceReviewService, DirectReviewService designReviewService) {
        this.inferenceReviewService = inferenceReviewService;
        this.designReviewService = designReviewService;
    }

    @GetMapping()
    @ResponseBody()
    public String healthCheckToInferenceEngine() {
        return inferenceReviewService.healthCheckToInferenceEngine();
    }

    @PostMapping("review/inference")
    public String reviewFromInferenceEngine(@RequestParam("target") String target, @RequestParam("file") MultipartFile csvFile) {
        inferenceReviewService.reviewFromInferenceEngine(target, csvFile);

        return "redirect:/";
    }

    @PostMapping("review/direct")
    public String reviewDirect(@RequestParam("target") String target,
                               @RequestParam("file") MultipartFile csvFile,
                               RedirectAttributes redirectAttributes) throws Exception {
        String generatedReportUrl = designReviewService.directReview(target, csvFile);

        redirectAttributes.addAttribute("report-url", generatedReportUrl);
        return "redirect:/result";
    }
}


