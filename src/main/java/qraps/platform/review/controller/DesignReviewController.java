package qraps.platform.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qraps.platform.global.error.exception.BusinessException;
import qraps.platform.global.error.exception.ErrorCode;
import qraps.platform.review.dto.CreateExcelDto;
import qraps.platform.review.dto.CreateExcelDto.ExcelHeaderRowDto;
import qraps.platform.review.dto.DirectExcelMapperDto;
import qraps.platform.review.dto.ResponseReviewDto;
import qraps.platform.review.entity.PartList;
import qraps.platform.review.service.CacheService;
import qraps.platform.review.service.ExcelService;
import qraps.platform.review.service.ExpertSystemReviewService;
import qraps.platform.review.service.ValidationService;
import qraps.platform.utils.CustomMultipartFile;
import qraps.platform.web.controller.dto.ReviewPageDto;
import qraps.platform.web.controller.dto.ValidateTarget;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class DesignReviewController {
    private ValidationService validationService;
    private ExcelService excelService;

    private CacheService cacheService;

    private ExpertSystemReviewService expertSystemReviewService;

    @Autowired
    DesignReviewController(ValidationService validationService, ExcelService excelService, CacheService cacheService, ExpertSystemReviewService expertSystemReviewService) {
        this.validationService = validationService;
        this.excelService = excelService;
        this.cacheService = cacheService;
        this.expertSystemReviewService = expertSystemReviewService;
    }

    @ResponseBody
    @GetMapping("review/partlist")
    public List<PartList> findPartList(Model model, @RequestParam ValidateTarget validateTarget) {
        List<PartList> parts = validationService.findAllPartListOrderByPartNo(validateTarget);
        model.addAttribute("parts", parts);

        return parts;
    }


    /**
     * 검증센터 partNo로 검증 대상 조회 API
     */
    @ResponseBody
    @GetMapping("review/target")
    public ReviewPageDto findPartListByPartNo(@RequestParam String partNo) {
        return validationService.findTargetByPartNo(partNo);
    }

    @PostMapping("template")
    public String stepDownICTemplatePage(@RequestBody ReviewPageDto reviewDto, RedirectAttributes redirectAttributes) {

        if (reviewDto == null) {
            System.out.println("template page's reviewDto is null");
            /** Todo: Exception handling */
            return "redirect:/validation_center";
        }

        /** Todo: search DB */
        Object entity = validationService.findEntity(reviewDto);


        String cacheEntityKey = reviewDto.getValidTarget().getTarget() + "/" + reviewDto.getPartNo();

        cacheService.storeValue(cacheEntityKey, entity);

        redirectAttributes.addAttribute("partNo", reviewDto.getPartNo());
        redirectAttributes.addAttribute("validTarget", reviewDto.getValidTarget());
        switch (reviewDto.getValidTarget()) {
            case IC:
                return "redirect:/validation_page_1";
            case TRANSISTOR:
                return "redirect:/validation_page_2";
            case DIODE:
                return "redirect:/validation_page_3";
        }


        return "redirect:/validation_center";
    }

    @PostMapping(value = "save/excel")
    public String generateExcel(@RequestBody CreateExcelDto createExcelDto, HttpSession session) throws Exception {

        ReviewPageDto reviewDto = ReviewPageDto.builder()
                .partNo(createExcelDto.getPartNo())
                .validTarget(createExcelDto.getValidateTarget())
                .build();

        List<ExcelHeaderRowDto> excelHeaderRowFields = createExcelDto.getExcelHeaderRowFields();
        excelHeaderRowFields.forEach(item -> System.out.println(item.getPartName() + " : " + item.getDesignValue()));
        List<DirectExcelMapperDto> excelRowFields = createExcelDto.getExcelRowFields();
        ByteArrayOutputStream excelFile = excelService.createExcelFile(excelHeaderRowFields, excelRowFields);

        CustomMultipartFile multipartFile = new CustomMultipartFile(createExcelDto.getValidateTarget().getTarget() + ".xlsx", excelFile.toByteArray());

        ResponseReviewDto reviewResult = expertSystemReviewService.reviewFromExpertSystem(reviewDto, multipartFile, session.getId());
        session.setAttribute("reviewResult", reviewResult);


        switch (reviewDto.getValidTarget()) {
            case IC:
                return "redirect:/validation_result_page_1";

            case TRANSISTOR:
                return "redirect:/validation_result_page_2";

            case DIODE:
                return "redirect:/validation_result_page_3";

            default:
                throw new BusinessException("엑셀 생성중 문제가 발생하였습니다. target/partNo: " + createExcelDto.getValidateTarget().getTarget() + "/" + createExcelDto.getPartNo(), ErrorCode.INVALID_INPUT_VALUE);
        }
    }

}
