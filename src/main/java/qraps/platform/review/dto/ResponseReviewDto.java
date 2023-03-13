package qraps.platform.review.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ResponseReviewDto {
    private String partNo;
    private List<ReviewDto.Result> reviewResults;
    private boolean passReview;

    //"type": "step_down",
    //private String type;

    //"manufacturer_name": "TI",
    //private String manufacturerName;

    public ResponseReviewDto() {
    }

}



