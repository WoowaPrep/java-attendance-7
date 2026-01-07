package attendance.exception;

public enum ErrorMessage {

    EMPTY_INPUT("입력값이 비어있습니다."),
    INVALID_NUMBER_FORMAT("숫자 형식이 올바르지 않습니다."),

    FILE_NOT_FOUND("파일을 찾을 수 없습니다."),
    FILE_READ_FAILED("파일을 읽을 수 없습니다."),

    INVALID_INPUT("잘못된 형식을 입력하였습니다."),
    ;

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return message;
    }
}
