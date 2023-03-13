package qraps.platform.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.global.error.exception.EntityNotFoundException;
import qraps.platform.review.dto.ExpertExcelMapperDto;
import qraps.platform.review.dto.ResponseReviewDto;
import qraps.platform.review.dto.ReviewDto;
import qraps.platform.review.dto.ValidateResultDto;
import qraps.platform.review.service.CacheService;
import qraps.platform.review.service.ExpertSystemReviewService;
import qraps.platform.review.service.ValidationService;
import qraps.platform.web.controller.dto.ReviewPageDto;

import javax.servlet.http.HttpSession;

@Tag(name = "ExpertSystemReview", description = "전문가 시스템을 통한 검증 API")
@Controller
public class DesignExpertSystemReviewController {

    private final ExpertSystemReviewService expertSystemReviewService;
    private final ValidationService validationService;
    private final CacheService cacheService;

    @Autowired
    public DesignExpertSystemReviewController(ExpertSystemReviewService expertSystemReviewService, ValidationService validationService, CacheService cacheService) {
        this.expertSystemReviewService = expertSystemReviewService;
        this.validationService = validationService;
        this.cacheService = cacheService;
    }

    /**
     * 사용자가 브라우저에서 호출하는 API
     */
    @PostMapping(path = "review/expert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String reviewFromExpertSystem(@ModelAttribute("reviewDto") ReviewPageDto reviewDto,
                                         @RequestParam("file_input") MultipartFile uploadedFile,
                                         HttpSession session) {


        session.setAttribute("reviewDto", reviewDto);
        System.out.println(uploadedFile.getName());
        System.out.println(uploadedFile.getContentType());
        ResponseReviewDto reviewResult = expertSystemReviewService.reviewFromExpertSystem(reviewDto, uploadedFile, session.getId());

        session.setAttribute("reviewResult", reviewResult);


        System.out.println("Validate target");
        System.out.println(reviewDto.getValidTarget());

        switch (reviewDto.getValidTarget()) {
            case IC:
                return "redirect:/validation_result_page_1";

            case TRANSISTOR:
                return "redirect:/validation_result_page_2";

            case DIODE:
                return "redirect:/validation_result_page_3";

        }


        return "redirect:/validation_center";
    }


    /**
     * Todo: Request 사용자 ID 필요
     */
    @Operation(summary = "데이터베이스 설계값 검증 시작 API",
            description = "전문가 시스템에서 최초 1회 호출하는 API\n" +
                    "처음 데이터베이스에서 검증 대상 조회\n" +
                    "세션에 조회 결과 저장\n" +
                    "review/part API 1회 요청마다 세션에 저장된 엔티티를 사용합니다\n.")
    @ResponseBody
    @PostMapping("review/start")
    public ReviewDto.Start startReviewTransaction(@RequestParam String sessionId, @RequestBody ReviewPageDto reviewDto) {

        ReviewDto.Verification verificationDto = validationService.getVerificationDto(reviewDto);

        String verificationDtoKey = "verificationDto/" + sessionId + "/" + reviewDto.getPartNo();
        String reviewDtoKey = "reviewDto/" + sessionId + "/" + reviewDto.getPartNo();

        cacheService.storeValue(verificationDtoKey, verificationDto);
        cacheService.storeValue(reviewDtoKey, reviewDto);


        System.out.println("review/start");
        System.out.println(reviewDto.toString());
        return ReviewDto.Start.builder()
                .start_check(true)
                .build();
    }


    @Operation(summary = "데이터베이스 설계값 검증 API",
            description = "전문가 시스템이 엑셀 파싱이후, 파츠 1개(엑셀 1행) 값을 데이터베이스와 비교하는 API입니다.\n" +
                    "검증할 소자의 개수 만큼 API를 호출하면 됩니다.\n" +
                    "검증할 소자의 partNo, 검증 대상의 이름, 검증 대상의 설계값이 필수 항목입니다.\n")
    @ResponseBody
    @PostMapping("review/part")
    public ValidateResultDto validatePart(@RequestBody ExpertExcelMapperDto excelRow, @RequestParam String sessionId) {
        System.out.println("Part Session ID: " + sessionId);
        System.out.println(excelRow.getPartNo());
        System.out.println(excelRow.getDesignValue());

        String verificationDtoKey = "verificationDto/" + sessionId + "/" + excelRow.getPartNo();
        ReviewDto.Verification verificationDto = (ReviewDto.Verification) cacheService.getValue(verificationDtoKey)
                .orElseThrow(() -> new EntityNotFoundException("검증 대상이 서버 메모리에 존재하지 않습니다."));

        return validationService.validate(excelRow, verificationDto);
    }

}
