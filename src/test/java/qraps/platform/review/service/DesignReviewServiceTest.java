package qraps.platform.review.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import qraps.platform.utils.csv.MockHelper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

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
        byte[] csvFile = MockHelper.getTestMockFileStream(fileName).readAllBytes();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", csvFile);

        //when
        String result = designReviewService.designReview("target", mockMultipartFile);

        //then
        assertThat(result).isEqualTo("success");
    }


}
