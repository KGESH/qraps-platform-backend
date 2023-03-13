package qraps.platform.review.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import qraps.platform.utils.MockHelper;

class DirectReviewServiceTest {

    private DirectReviewService designReviewService;


    @BeforeEach
    void init() {
//        designReviewService = new DirectReviewService(new CsvParserService(), new ReportService(), excelParseService);
    }


    @Test
    void CSV_파싱_성공() throws Exception {
        //given
        String fileName = "csv_example.csv";
        byte[] csvFile = MockHelper.getTestMockFileStream(fileName).readAllBytes();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", csvFile);

        //when
//        String result = designReviewService.directReview("target", mockMultipartFile);

        //then
//        assertThat(result).isEqualTo("success");
    }


}
