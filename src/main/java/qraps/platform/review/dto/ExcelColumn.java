package qraps.platform.review.dto;

public enum ExcelColumn {
    PART_NAME(0, "검증 항목"),
    NOTE(1, "비고"),
    UNIT(2, "단위"),

    DESIGN_VALUE(3, "설계 "),

    NEED_VALIDATE(4, "적용여부");

    private final int index;
    private final String columnName;

    ExcelColumn(int index, String description) {
        this.index = index;
        this.columnName = description;
    }

    public int getIndex() {
        return index;
    }

    public String getColumnName() {
        return columnName;
    }
}
