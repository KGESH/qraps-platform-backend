package qraps.platform.report.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qraps.platform.utils.MockHelper;

import java.io.IOException;

@Service
public class ReportResultService {

    /**
     * Todo: impl
     */
    public byte[] getReportWithCoverPage(String reportUrl, MultipartFile coverPage) throws IOException {
        byte[] report = getReport(reportUrl);

        /** Todo: merge cover and report by PDF BOX Library */
        byte[] merged = new byte[0];
        return merged;
    }

    public byte[] getReport(String reportUrl) throws IOException {
        return findReportByUrl(reportUrl);
    }

    /**
     * Todo: replace to S3
     */
    private byte[] findReportByUrl(String reportUrl) throws IOException {
        return MockHelper.getMockFileStreamByUrl(reportUrl).readAllBytes();
    }


}
