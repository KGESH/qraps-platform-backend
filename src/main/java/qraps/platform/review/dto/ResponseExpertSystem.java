package qraps.platform.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class ResponseExpertSystem {

    @Getter
    @Setter
//    @Builder
    public static class Result {
        private String targetName;
        private List<ReviewDto.Result> reviewResults;
        private boolean passReview;
    }

}
