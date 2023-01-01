package qraps.platform.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String validationCenterPage() {
        return "validation_center";
    }

    @GetMapping("validation_result")
    public String validationResultPage() {
        return "validation_result";
    }

}
