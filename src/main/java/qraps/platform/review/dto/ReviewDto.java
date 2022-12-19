package qraps.platform.review.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


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
    @ToString
    public static class Result {
        private String propertyName;
        private Object value;
        private boolean isValid;
    }
}
