package qraps.platform.web.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewPageDto {
    private ValidateTarget validTarget;
    private String partNo;
}
