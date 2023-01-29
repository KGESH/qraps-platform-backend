package qraps.platform.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "C1", "요청한 값이 잘못되었습니다."),
    ENTITY_NOT_FOUND(400, "C2", "엔티티를 찾을 수 없습니다."),
    INVALID_TYPE_VALUE(400, "C3", "요청한 값의 타입이 잘못되었습니다."),
    METHOD_NOT_ALLOWED(405, "C4", "지원하지 않는 HTTP 메서드 입니다."),
    INTERNAL_SERVER_ERROR(500, "C5", "Server Error");


    private final int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
