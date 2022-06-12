package joins.pms.core.api;

public enum ApiStatus {
    SUCCESS(0, "정상"),
    ABNORMAL_ACCESS(-100, "비정상적인 접근입니다."),
    REQUIRED_PARAMETER_IS_NOT_FOUND(-101, "필수 파라미터가 존재하지 않습니다."),
    IDENTIFY_NEEDS_EMPTY(-102, "식별값이 비어있어야 합니다."),
    IDENTIFY_NEEDS_NOT_EMPTY(-103, "식별값이 필요합니다."),
    DATA_IS_EMPTY(-200, "데이터가 존재하지 않습니다."),
    FAILED_TO_LOGIN(-201, "이메일 주소 혹은 비밀번호가 올바르지 않습니다.")
    ;

    private final int code;
    private final String message;
    ApiStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode () {
        return this.code;
    }
    public String getMessage () {
        return this.message;
    }
}
