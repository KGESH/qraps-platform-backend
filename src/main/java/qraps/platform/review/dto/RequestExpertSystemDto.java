package qraps.platform.review.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class RequestExpertSystemDto {
    private String target;
    private MultipartFile file;
}
