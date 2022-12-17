package qraps.platform.result.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import qraps.platform.utils.csv.MockHelper;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewResultServiceTest {
    private ReviewResultService reviewResultService;

    @BeforeEach
    void init() {
        reviewResultService = new ReviewResultService();
    }

    @Test
    void 리포트_템플릿_컴파일_성공() throws Exception {
        //given
        String templateFileName = "example.jrxml";
        byte[] templateFile = MockHelper.getTestMockFileStream(templateFileName).readAllBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", templateFile);

        //when
        ResponseEntity<byte[]> responseEntity = reviewResultService.generateReport(mockMultipartFile);
        String generatedFileName = responseEntity.getHeaders().getContentDisposition().getFilename();
        int statusCode = responseEntity.getStatusCodeValue();


        //then
        assertThat(generatedFileName).isEqualTo("report.pdf");
        assertThat(statusCode).isEqualTo(200);
    }

}
