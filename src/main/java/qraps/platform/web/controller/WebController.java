package qraps.platform.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import qraps.platform.global.error.exception.BusinessException;
import qraps.platform.global.error.exception.ErrorCode;
import qraps.platform.review.dto.ResponseReviewDto;
import qraps.platform.review.dto.ReviewDto.Verification;
import qraps.platform.review.entity.Diode;
import qraps.platform.review.entity.PartList;
import qraps.platform.review.entity.StepDownIC;
import qraps.platform.review.entity.Transistor;
import qraps.platform.review.service.CacheService;
import qraps.platform.utils.EntityHelper;
import qraps.platform.utils.ImageUrlHelper;
import qraps.platform.utils.ThymeleafHelper;
import qraps.platform.web.controller.dto.ReviewPageDto;
import qraps.platform.web.controller.dto.ValidateTarget;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class WebController {

    private CacheService cacheService;

    @Autowired
    public WebController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

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
    public String validationCenterPage(Model model, HttpSession session) {

        ReviewPageDto reviewDto = (ReviewPageDto) session.getAttribute("reviewDto");
        System.out.println("validation_center DTO");
        if (reviewDto == null) {
            System.out.println("review dto is null");
            reviewDto = new ReviewPageDto();
        }

        List<PartList> parts = (List<PartList>) model.getAttribute("parts");
        if (parts != null) {
            System.out.println("add parts");
            model.addAttribute("parts", parts);
        }


        model.addAttribute("reviewDto", reviewDto);
        model.addAttribute("targets", ValidateTarget.values());
        session.setAttribute("reviewDto", reviewDto);

        return "validation_center";
    }

    @GetMapping("validation_result")
    public String validationResultPage(@SessionAttribute(name = "reviewResult", required = false) ResponseReviewDto reviewResult, Model model) {

        if (reviewResult == null) {
            return "redirect:/main";
        }

        if (reviewResult.isPassReview()) {
            model.addAttribute("validateResult", "PASS");
        } else {
            model.addAttribute("validateResult", "FAIL");
        }


        System.out.println(reviewResult.toString());

        return "validation_result";
    }

    @GetMapping("validation_result_page_1")
    public String sdicValidationResultPage(@SessionAttribute(name = "reviewResult", required = false) ResponseReviewDto reviewResult, HttpSession session, Model model) {

        if (reviewResult == null) {
            return "redirect:/validation_center";
        }
        String sessionId = session.getId();

        String verificationDtoKey = "verificationDto/" + sessionId + "/" + reviewResult.getPartNo();
        Verification verificationDto = (Verification) cacheService.getValue(verificationDtoKey)
                .orElseThrow(() -> new BusinessException("캐시에 저장된 StepDown IC 검증 객체가 없습니다. partNo: " + reviewResult.getPartNo(), ErrorCode.ENTITY_NOT_FOUND));


        Map<String, Object> resultsMap = new HashMap<>();
        reviewResult.getReviewResults()
                .stream()
                .forEach(result -> {
                    resultsMap.put(result.getPartName(), result.getDesignValue());
                    resultsMap.put("result/" + result.getPartName(), result.getPassString());
                });


        ImageUrlHelper imageUrlHelper = new ImageUrlHelper();
        model.addAttribute("urlHelper", imageUrlHelper);

        StepDownIC referenceEntity = EntityHelper.convertMapToEntity(verificationDto.getTarget(), StepDownIC.class);
        model.addAttribute("sdic", referenceEntity);

        model.addAttribute("passReview", reviewResult.isPassReview() ? "PASS" : "FAIL");
        model.addAttribute("resultsMap", resultsMap);


        ThymeleafHelper helper = new ThymeleafHelper();
        model.addAttribute("helper", helper);


        if (reviewResult.getPartNo() == null) {
            return "redirect:/main";
        }

        model.addAttribute("reviewResult", reviewResult);

        return "validation_result_page_1";
    }


    @GetMapping("validation_result_page_2")
    public String transistorValidationResultPage(@SessionAttribute(name = "reviewResult", required = false) ResponseReviewDto reviewResult, HttpSession session, Model model) {

        if (reviewResult == null) {
            return "redirect:/validation_center";
        }

        String sessionId = session.getId();
        String verificationDtoKey = "verificationDto/" + sessionId + "/" + reviewResult.getPartNo();
        Verification verificationDto = (Verification) cacheService.getValue(verificationDtoKey)
                .orElseThrow(() -> new BusinessException("캐시에 저장된 Transistor 검증 객체가 없습니다. partNo: " + reviewResult.getPartNo(), ErrorCode.ENTITY_NOT_FOUND));


        Map<String, Object> resultsMap = new HashMap<>();
        reviewResult.getReviewResults()
                .stream()
                .forEach(result -> {
                    resultsMap.put(result.getPartName(), result.getDesignValue());
                    resultsMap.put("result/" + result.getPartName(), result.getPassString());
                });


        ImageUrlHelper imageUrlHelper = new ImageUrlHelper();
        model.addAttribute("urlHelper", imageUrlHelper);

        Transistor referenceEntity = EntityHelper.convertMapToEntity(verificationDto.getTarget(), Transistor.class);
        model.addAttribute("transistor", referenceEntity);

        model.addAttribute("passReview", reviewResult.isPassReview() ? "PASS" : "FAIL");
        model.addAttribute("resultsMap", resultsMap);


        ThymeleafHelper helper = new ThymeleafHelper();
        model.addAttribute("helper", helper);


        System.out.println("Result page");
        System.out.println(referenceEntity.toString());
        System.out.println(reviewResult.toString());
        System.out.println(reviewResult.getPartNo());

        if (reviewResult.getPartNo() == null) {
            return "redirect:/main";
        }

        model.addAttribute("reviewResult", reviewResult);

        return "validation_result_page_2";
    }


    @GetMapping("validation_result_page_3")
    public String diodeValidationResultPage(@SessionAttribute(name = "reviewResult", required = false) ResponseReviewDto reviewResult, HttpSession session, Model model) {

        if (reviewResult == null) {
            return "redirect:/validation_center";
        }

        String sessionId = session.getId();
        String verificationDtoKey = "verificationDto/" + sessionId + "/" + reviewResult.getPartNo();
        Verification verificationDto = (Verification) cacheService.getValue(verificationDtoKey)
                .orElseThrow(() -> new BusinessException("캐시에 저장된 Diode 검증 객체가 없습니다. partNo: " + reviewResult.getPartNo(), ErrorCode.ENTITY_NOT_FOUND));


        Map<String, Object> resultsMap = new HashMap<>();
        reviewResult.getReviewResults()
                .stream()
                .forEach(result -> {
                    resultsMap.put(result.getPartName(), result.getDesignValue());
                    resultsMap.put("result/" + result.getPartName(), result.getPassString());
                });


        ImageUrlHelper imageUrlHelper = new ImageUrlHelper();
        model.addAttribute("urlHelper", imageUrlHelper);

        Diode referenceEntity = EntityHelper.convertMapToEntity(verificationDto.getTarget(), Diode.class);
        model.addAttribute("diode", referenceEntity);
        model.addAttribute("passReview", reviewResult.isPassReview() ? "PASS" : "FAIL");
        model.addAttribute("resultsMap", resultsMap);


        ThymeleafHelper helper = new ThymeleafHelper();
        model.addAttribute("helper", helper);


        System.out.println("Result page");
        System.out.println(referenceEntity.toString());
        System.out.println(reviewResult.toString());
        System.out.println(reviewResult.getPartNo());

        if (reviewResult.getPartNo() == null) {
            return "redirect:/main";
        }

        model.addAttribute("reviewResult", reviewResult);

        return "validation_result_page_3";
    }


    @GetMapping("validation_page_1")
    public String sdicTemplatePage(
            @ModelAttribute("partNo") String partNo, @ModelAttribute("validTarget") ValidateTarget validateTarget, Model model) {

        if ((validateTarget == null) || partNo == null) {
            System.out.println("Redirect");
            return "redirect:/validation_center";
        }

        System.out.println("reviewDto: p1");
        System.out.println(partNo);
        System.out.println(validateTarget.getTarget());

        String cacheEntityKey = validateTarget.getTarget() + "/" + partNo;
        Optional<Object> cachedEntity = cacheService.getValue(cacheEntityKey);

        if (cachedEntity.isEmpty()) {
            return "redirect:/validation_center";
        }

        StepDownIC sdic = (StepDownIC) cachedEntity.get();

        System.out.println(sdic.getPartNo());
        System.out.println(sdic.getPackage_type());


        ImageUrlHelper imageUrlHelper = new ImageUrlHelper();
        model.addAttribute("urlHelper", imageUrlHelper);
        model.addAttribute("sdic", sdic);
        return "validation_page_1";
    }

    @GetMapping("validation_page_2")
    public String transistorTemplatePage(
            @ModelAttribute("partNo") String partNo, @ModelAttribute("validTarget") ValidateTarget validateTarget, Model model) {

        if ((validateTarget == null) || partNo == null) {
            System.out.println("Redirect");
            return "redirect:/validation_center";
        }

        System.out.println("reviewDto: p2");
        System.out.println(partNo);
        System.out.println(validateTarget.getTarget());

        String cacheEntityKey = validateTarget.getTarget() + "/" + partNo;
        Optional<Object> cachedEntity = cacheService.getValue(cacheEntityKey);

        if (cachedEntity.isEmpty()) {
            return "redirect:/validation_center";
        }

        Transistor transistor = (Transistor) cachedEntity.get();

        System.out.println(transistor.getPartNo());
        System.out.println(transistor.getPackage_type());


        ImageUrlHelper imageUrlHelper = new ImageUrlHelper();
        model.addAttribute("urlHelper", imageUrlHelper);
        model.addAttribute("transistor", transistor);
        return "validation_page_2";
    }

    @GetMapping("validation_page_3")
    public String diodeTemplatePage(
            @ModelAttribute("partNo") String partNo, @ModelAttribute("validTarget") ValidateTarget validateTarget, Model model) {

        if ((validateTarget == null) || partNo == null) {
            System.out.println("Redirect");
            return "redirect:/validation_center";
        }

        System.out.println("reviewDto p3: ");
        System.out.println(partNo);
        System.out.println(validateTarget.getTarget());

        String cacheEntityKey = validateTarget.getTarget() + "/" + partNo;
        Optional<Object> cachedEntity = cacheService.getValue(cacheEntityKey);

        if (cachedEntity.isEmpty()) {
            return "redirect:/validation_center";
        }

        Diode diode = (Diode) cachedEntity.get();

        System.out.println(diode.getPartNo());
        System.out.println(diode.getPackage_type());


        ImageUrlHelper imageUrlHelper = new ImageUrlHelper();
        model.addAttribute("urlHelper", imageUrlHelper);
        model.addAttribute("diode", diode);
        return "validation_page_3";
    }

}
