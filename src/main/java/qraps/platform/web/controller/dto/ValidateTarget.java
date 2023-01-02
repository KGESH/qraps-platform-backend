package qraps.platform.web.controller.dto;

/**
 * validation_center.html
 * 검증 대상 선택 드롭박스 키값, 설명 맵핑
 */
public enum ValidateTarget {
    IC("IC", "Step-down IC"),
    TRANSISTOR("Transistor", "Transistor"),
    DIODE("Diode", "Diode");

    private final String target;
    private final String description;

    ValidateTarget(String target, String description) {
        this.target = target;
        this.description = description;
    }

    public String getTarget() {
        return target;
    }

    public String getDescription() {
        return description;
    }
}
