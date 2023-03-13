package qraps.platform.report.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import qraps.platform.report.dto.ReportDto;
import qraps.platform.review.dto.ResponseReviewDto;
import qraps.platform.review.dto.ReviewDto;
import qraps.platform.utils.MockHelper;
import qraps.platform.web.controller.dto.ValidateTarget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {


    public byte[] generateReport(ResponseReviewDto reviewResult) throws Exception {
        InputStream reportTemplate = getReportTemplate();
        byte[] report = compileReportToPdf(reviewResult, reportTemplate);
        return report;
    }

    public String generateReport(ReportDto.Generate reportDto, List<ReviewDto.Result> reviewResults) throws Exception {

        InputStream reportTemplate = getReportTemplate();

        byte[] report = compileReportToPdf(reportDto.getTargetName() + " : " + reportDto.getReportId(), reviewResults, reportTemplate);

        ReportDto.Upload uploadDto = new ReportDto.Upload(reportDto, report);
        String savedUrl = saveReport(uploadDto);

        return savedUrl;
    }

    private byte[] compileReportToPdf(String title, List<ReviewDto.Result> reviewResults, InputStream jasperReportTemplateFileStream) throws Exception {

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(reviewResults);

        JasperReport report = JasperCompileManager.compileReport(jasperReportTemplateFileStream);


        /** Todo: replace to DTO */
        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("title", title);

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, reportParams, beanCollectionDataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }

    private byte[] compileReportToPdf(ResponseReviewDto reviewResult, InputStream jasperReportTemplateFileStream) throws Exception {

        JRBeanCollectionDataSource fieldsDataSource = new JRBeanCollectionDataSource(reviewResult.getReviewResults());
        JasperReport report = JasperCompileManager.compileReport(jasperReportTemplateFileStream);
        Map<String, Object> reportParams = new HashMap<>();


        // 소자 체크 DATA: Step Down IC
        String target = ValidateTarget.IC.getTarget();
        String partNo = "";


        String passResult = reviewResult.isPassReview() ? "PASS" : "FAIL";
        reportParams.put("title", reviewResult.getPartNo());
        reportParams.put("passResult", passResult);

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, reportParams, fieldsDataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);


        return outputStream.toByteArray();
    }

    /**
     * Todo: replace to S3
     */
    private String saveReport(ReportDto.Upload dto) throws IOException {
        Path reportUrl = Path.of("src/main/resources/static/", dto.getFileName());

        Path savedUrl = Files.write(reportUrl, dto.getFileBytes());

        return savedUrl.toString();
    }


    public InputStream getReportTemplate() throws IOException {
        return MockHelper.getMockFileStream("example.jrxml");
    }


}
