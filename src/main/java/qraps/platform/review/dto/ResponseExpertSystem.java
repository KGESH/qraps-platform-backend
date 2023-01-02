package qraps.platform.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ResponseExpertSystem {

    @Getter
    @Setter
    @Builder
    public static class Result {
        private String targetName;
        private List<ReviewDto.Result> reviewResults;
        private boolean isPass;
    }
}
