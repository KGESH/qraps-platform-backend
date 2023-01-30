package qraps.platform.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import qraps.platform.review.dto.ResponseReviewDto;
import qraps.platform.web.controller.dto.ReviewPageDto;
import qraps.platform.web.controller.dto.ValidateTarget;

@Controller
public class WebController {

    @GetMapping("/")
    public String redirectMain() {
        return "redirect:/main";
    }

    @GetMapping("main")
    public String mainPage(Model model) {
        return "main";
    }

    @GetMapping("business")
    public String businessPage(Model model) {
        return "business";
    }

    @GetMapping("validation_center")
    public String validationCenterPage(Model model) {
        model.addAttribute("reviewDto", new ReviewPageDto());
        model.addAttribute("targets", ValidateTarget.values());
        return "validation_center";
    }

    @GetMapping("validation_result")
    public String validationResultPage(@SessionAttribute(name = "reviewResult", required = false) ResponseReviewDto reviewResult,
                                       Model model) {

        if (reviewResult == null) {
            return "redirect:/main";
        }

        if (reviewResult.isPassReview()) {
            model.addAttribute("validateResult", "PASS");
        } else {
            model.addAttribute("validateResult", "FAIL");
        }

        return "validation_result";
    }

    @GetMapping("result/sdic")
    public String stepDownICResultPage() {
        return "validation_page_1";
    }
}
