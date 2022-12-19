package qraps.platform.utils;

import qraps.platform.review.dto.ReviewDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockHelper {

    public static FileInputStream getMockFileStreamByUrl(String fileUrl) throws IOException {
        return new FileInputStream(new File(fileUrl));
    }


    public static FileInputStream getMockFileStream(String fileName) throws IOException {
        String url = "src/main/resources/static/" + fileName;
        return new FileInputStream(new File(url));
    }

    public static FileInputStream getTestMockFileStream(String fileName) throws IOException {
        String url = "src/test/java/resources/static/" + fileName;
        return new FileInputStream(new File(url));
    }

    public static List<ReviewDto.CsvPositionMapper> getMockResult() {
        List<ReviewDto.CsvPositionMapper> mockList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ReviewDto.CsvPositionMapper mockItem = ReviewDto.CsvPositionMapper.builder()
                    .name("mock-name" + i)
                    .valueOne("value One " + i)
                    .valueTwo("value Two " + i)
                    .valueThree("value Three " + i)
                    .valueFour("value Four " + i)
                    .build();

            mockList.add(mockItem);
        }

        return mockList;
    }

}
