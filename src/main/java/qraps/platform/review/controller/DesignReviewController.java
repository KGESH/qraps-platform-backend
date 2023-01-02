package qraps.platform.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qraps.platform.review.dto.ResponseExpertSystem;
import qraps.platform.review.service.DirectReviewService;
import qraps.platform.review.service.ExpertSystemReviewService;
import qraps.platform.web.controller.dto.ReviewPageDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class DesignReviewController {

    private final ExpertSystemReviewService expertSystemReviewService;
    private final DirectReviewService designReviewService;

    @Autowired
    public DesignReviewController(ExpertSystemReviewService expertSystemReviewService, DirectReviewService designReviewService) {
        this.expertSystemReviewService = expertSystemReviewService;
        this.designReviewService = designReviewService;
    }

    @PostMapping("review/expert")
    public String reviewFromExpertSystem(@ModelAttribute("reviewDto") ReviewPageDto reviewDto,
                                         @RequestParam("file_input") MultipartFile uploadedFile,
                                         HttpServletRequest request) {

        String validTarget = reviewDto.getValidTarget().getTarget();
        ResponseExpertSystem.Result reviewResult = expertSystemReviewService.reviewFromExpertSystem(validTarget, uploadedFile);

        HttpSession session = request.getSession();
        session.setAttribute("reviewResult", reviewResult);

        return "redirect:/validation_result";
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


