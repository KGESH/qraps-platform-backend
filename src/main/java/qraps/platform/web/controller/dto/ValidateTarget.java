package qraps.platform.web.controller.dto;

/**
 * validation_center.html
 * 검증 대상 선택 드롭박스 키값, 설명 맵핑
 */
public enum ValidateTarget {
    IC("IC", "Step-down IC", 1),
    TRANSISTOR("Transistor", "Transistor", 2),
    DIODE("Diode", "Diode", 3);

    private final String target;
    private final String description;

    private final int tableIndex;

    ValidateTarget(String target, String description, int tableIndex) {
        this.target = target;
        this.description = description;
        this.tableIndex = tableIndex;
    }

    public String getTarget() {
        return target;
    }

    public String getDescription() {
        return description;
    }

    public int getTableIndex() {
        return tableIndex;
    }
}
