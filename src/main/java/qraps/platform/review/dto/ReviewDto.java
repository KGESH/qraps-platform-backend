package qraps.platform.review.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;


public class ReviewDto {

    @Getter
    @Builder
    public static class RequestExpertSystem {
        private String target;
        private MultipartFile file;
    }


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


    /**
     * Jasper studio 템플릿과 맵핑
     * 템플릿의 프로퍼티 이름과
     * 클래스 멤버 변수 이름 일치 필요
     */
    @Getter
    @Setter
    @ToString
    public static class Result {
        private String partName;
        private Object designValue;
        private boolean passValidate;

        public Result() {
        }

        /**
         * Jasper report
         * pass, fail 문자 출력
         */
        public String getPassString() {
            return passValidate ? "O" : "X";
        }
    }
}
