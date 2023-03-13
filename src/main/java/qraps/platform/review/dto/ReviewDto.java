package qraps.platform.review.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

import java.util.Map;


public class ReviewDto {

    @Getter
    @Setter
    @Builder
    @ToString
    public static class CsvPositionMapper {

        @CsvBindByPosition(position = 0, required = true)
        private String name;

        @CsvBindByPosition(position = 0, required = true)
        private String valueOne;

        @CsvBindByPosition(position = 1, required = true)
        private String valueTwo;

        @CsvBindByPosition(position = 2, required = true)
        private String valueThree;

        @CsvBindByPosition(position = 3, required = true)
        private String valueFour;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    public static class CsvNameMapper {

        @CsvBindByName(column = "name", required = true)
        private String name;

        @CsvBindByName(column = "valueOne", required = true)
        private String valueOne;

        @CsvBindByName(column = "valueTwo", required = true)
        private String valueTwo;

        @CsvBindByName(column = "valueThree", required = true)
        private String valueThree;

        @CsvBindByName(column = "valueFour", required = true)
        private String valueFour;

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Start {
        private boolean start_check;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    public static class Verification {
        private Map<String, Object> criteria;
        private Map<String, Object> target;
    }


    /**
     * Jasper studio 템플릿과 맵핑
     * 템플릿의 프로퍼티 이름과
     * 클래스 멤버 변수 이름 일치 필요
     */
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @ToString
    public static class Result {
        private String partName;
        private Object designValue;
        private boolean verification;

        public Result() {
        }

        /**
         * Jasper report
         * pass, fail 문자 출력
         */
        public String getPassString() {
            return verification ? "OK" : "NG";
        }
    }
}
