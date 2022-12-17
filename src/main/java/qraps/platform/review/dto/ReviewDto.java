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
        private Integer id;

        @CsvBindByPosition(position = 1, required = true)
        private String name;

        @CsvBindByPosition(position = 2, required = true)
        private Integer age;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    public static class CsvNameMapper {
        @CsvBindByName(column = "id", required = true)
        private Integer id;

        @CsvBindByName(column = "name", required = true)
        private String name;

        @CsvBindByName(column = "age", required = true)
        private Integer age;
    }


    @Getter
    @Setter
    @ToString
    public static class InferenceEngineResult {
        private String propertyName;
        private Object value;
        private boolean isValid;
    }
}
