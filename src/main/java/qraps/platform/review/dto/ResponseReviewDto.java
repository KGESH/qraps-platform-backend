package qraps.platform.review.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ResponseReviewDto {
    private String targetName;
    private List<ReviewDto.Result> reviewResults;
    private boolean passReview;

    public ResponseReviewDto() {
    }
}
