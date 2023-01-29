package qraps.platform.review.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import qraps.platform.global.error.exception.BusinessException;
import qraps.platform.global.error.exception.ErrorCode;
import qraps.platform.review.dto.ResponseReviewDto;
import qraps.platform.review.service.DirectReviewService;
import qraps.platform.review.service.ValidationService;
import qraps.platform.web.controller.dto.ReviewPageDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

@Tag(name = "DirectReview", description = "플랫폼을 통한 직접 검증 API")
@Controller
public class DesignDirectReviewController {

    private final DirectReviewService designReviewService;
    private TemplateEngine templateEngine;

    @Autowired
    public DesignDirectReviewController(DirectReviewService designReviewService, ValidationService validationService, TemplateEngine templateEngine) {
        this.designReviewService = designReviewService;
        this.templateEngine = templateEngine;
    }


    @PostMapping(path = "review/direct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String review(@ModelAttribute("reviewDto") ReviewPageDto reviewDto, @RequestParam(value = "file_input") MultipartFile uploadedFile, HttpServletRequest request) throws Exception {
        if (uploadedFile.isEmpty()) {
            throw new BusinessException("검증 엑셀 파일이 누락되었습니다.", ErrorCode.INVALID_INPUT_VALUE);
        }

        ResponseReviewDto reviewResult = designReviewService.directReview(reviewDto.getValidTarget(), uploadedFile);

        HttpSession session = request.getSession();
        session.setAttribute("reviewResult", reviewResult);

        return "redirect:/validation_result";
    }


    /**
     * Todo: remove
     */
    @GetMapping("jasper/pdf")
    @ResponseBody
    public ResponseEntity<byte[]> generateJasperPdf() throws JRException {


        Context context = new Context();

//        InputStream templatePath = this.getClass().getResourceAsStream("/static/example.jrxml");
        String templateName = "example";
        String renderedHtmlContent = templateEngine.process(templateName, context);
        InputStream inputStream = new ByteArrayInputStream(renderedHtmlContent.getBytes());

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>());

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCreatingBatchModeBookmarks(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        String fileName = "example" + "_report.pdf";
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");


        byte[] pdfBytes = outputStream.toByteArray();
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(pdfBytes);
    }

}
