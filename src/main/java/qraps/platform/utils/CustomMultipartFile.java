package qraps.platform.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CustomMultipartFile implements MultipartFile {
    private final byte[] bytes;
    private final String filename;

    public CustomMultipartFile(String filename, byte[] bytes) {
        this.bytes = bytes;
        this.filename = filename;
    }

    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return filename;
    }

    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return filename;
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return null;
    }

    @Override
    public boolean isEmpty() {
        return bytes == null || bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(bytes);
    }
}
