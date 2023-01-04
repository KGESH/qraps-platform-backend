package qraps.platform.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qraps.platform.review.dto.ExcelMapperDto;
import qraps.platform.review.dto.ResponseExpertSystem;
import qraps.platform.review.dto.ValidateResultDto;
import qraps.platform.review.service.DirectReviewService;
import qraps.platform.review.service.ExpertSystemReviewService;
import qraps.platform.review.service.ValidationService;
import qraps.platform.web.controller.dto.ReviewPageDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Tag(name = "Review", description = "검증 API")
@Controller
public class DesignReviewController {

    private final ExpertSystemReviewService expertSystemReviewService;
    private final DirectReviewService designReviewService;
    private final ValidationService validationService;

    @Autowired
    public DesignReviewController(ExpertSystemReviewService expertSystemReviewService, DirectReviewService designReviewService, ValidationService validationService) {
        this.expertSystemReviewService = expertSystemReviewService;
        this.designReviewService = designReviewService;
        this.validationService = validationService;
    }

    @RequestMapping(
            path = "review/expert",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
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

    @Operation(summary = "데이터베이스에서 설계값 검증 API",
            description = "전문가 시스템이 엑셀 파싱이후, 파츠 1개(엑셀 1행) 값을 데이터베이스와 비교하는 API입니다. " +
                    "검증할 소자의 개수 만큼 API를 호출하면 됩니다. " +
                    "현재 임시로 무조건 valid: true 를 리턴합니다.")
    @ResponseBody
    @PostMapping("review/part")
    public ValidateResultDto validatePart(@RequestBody ExcelMapperDto excelRow) {
        System.out.println("call validate part");
        ValidateResultDto validateResult = validationService.validate(excelRow);
        return validateResult;
    }
}


