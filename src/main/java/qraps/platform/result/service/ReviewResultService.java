package qraps.platform.result.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.utils.csv.MockHelper;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static qraps.platform.review.dto.ReviewDto.CsvPositionMapper;

@Service
public class ReviewResultService {

    public ResponseEntity<byte[]> generateReport(MultipartFile jasperReportTemplateFile) throws Exception {

        byte[] report = compileReportToPdf(jasperReportTemplateFile);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.pdf\"");

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(report);
    }


    private byte[] compileReportToPdf(MultipartFile jasperReportTemplateFile) throws Exception {

        List<CsvPositionMapper> mockList = MockHelper.getMockResult();
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(mockList);

        JasperReport report = JasperCompileManager.compileReport(jasperReportTemplateFile.getInputStream());


        /** Todo: replace to DTO */
        Map<String, Object> params = new HashMap<>();
        params.put("title", "My report");

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, beanCollectionDataSource);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }


}
