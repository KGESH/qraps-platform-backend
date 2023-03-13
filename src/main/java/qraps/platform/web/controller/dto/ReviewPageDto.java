package qraps.platform.web.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewPageDto {
    @Schema(type = "String", allowableValues = {"IC", "TRANSISTOR", "DIODE"})
    private ValidateTarget validTarget;
    private String partNo;

}
