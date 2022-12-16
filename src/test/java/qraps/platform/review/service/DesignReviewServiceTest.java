package qraps.platform.review.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class DesignReviewServiceTest {

    private DesignReviewService designReviewService;


    @BeforeEach
    void init() {
        designReviewService = new DesignReviewService();
    }


    @Test
    void CSV_파싱_성공() throws IOException {
        //given
        String fileName = "csv_example.csv";
        MockMultipartFile mockMultipartFile = getMockMultiPartFile(fileName);

        //when
        String result = designReviewService.designReview("target", mockMultipartFile);

        //then
        assertThat(result).isEqualTo("success");
    }


    private MockMultipartFile getMockMultiPartFile(String fileName) throws IOException {
        String url = "src/test/java/resources/static/" + fileName;

        FileInputStream fileInputStream = new FileInputStream(new File(url));
        return new MockMultipartFile("file", fileInputStream.readAllBytes());
    }
}
