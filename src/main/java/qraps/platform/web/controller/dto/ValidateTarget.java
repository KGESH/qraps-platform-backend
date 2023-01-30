package qraps.platform.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import qraps.platform.global.error.exception.BusinessException;
import qraps.platform.global.error.exception.ErrorCode;

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

    @JsonCreator
    public static ValidateTarget from(String s) {
        switch (s) {
            case "IC":
                return ValidateTarget.IC;

            case "Transistor":
                return ValidateTarget.TRANSISTOR;

            case "Diode":
                return ValidateTarget.DIODE;

            default:
                throw new BusinessException("검증 대상이 잘못되었습니다. 입력 받은 검증 대상: " + s, ErrorCode.INVALID_INPUT_VALUE);
        }
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
