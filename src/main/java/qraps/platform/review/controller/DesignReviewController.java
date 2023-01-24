package qraps.platform.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import qraps.platform.review.entity.PartList;
import qraps.platform.review.service.ValidationService;
import qraps.platform.web.controller.dto.ReviewPageDto;
import qraps.platform.web.controller.dto.ValidateTarget;

import java.util.List;

@Controller
public class DesignReviewController {
    private ValidationService validationService;

    @Autowired
    DesignReviewController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @ResponseBody
    @GetMapping("review/partlist")
    public List<PartList> findPartList(@RequestParam ValidateTarget validateTarget) {
        return validationService.findAllPartListOrderByPartNo(validateTarget);
    }


    /**
     * 검증센터 partNo로 검증 대상 조회 API
     */
    @ResponseBody
    @GetMapping("review/target")
    public ReviewPageDto findPartListByPartNo(@RequestParam String partNo) {
        return validationService.findTargetByPartNo(partNo);
    }
}
