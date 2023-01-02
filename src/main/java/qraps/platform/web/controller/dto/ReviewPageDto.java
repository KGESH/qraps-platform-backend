package qraps.platform.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewPageDto {
    private ValidateTarget validTarget;
    private String targetNumber;
}
