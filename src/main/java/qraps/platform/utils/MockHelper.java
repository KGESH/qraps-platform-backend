package qraps.platform.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MockHelper {

    public static FileInputStream getMockFileStreamByUrl(String fileUrl) throws IOException {
        return new FileInputStream(new File(fileUrl));
    }


    public static InputStream getMockFileStream(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/" + fileName);
        return resource.getInputStream();
    }

    public static FileInputStream getTestMockFileStream(String fileName) throws IOException {
        String url = "src/test/java/resources/static/" + fileName;
        return new FileInputStream(new File(url));
    }

}
