package joins.pms.core.http;

public enum ApiStatus {
    SUCCESS(0, "정상"),
    /** User ( -100 ~ -199) */
    FAILED_SIGNUP(-100, "사용자 정보를 생성하는 과정에서 오류가 발생하였습니다."),
    FAILED_LOGIN(-101, "이메일 혹은 비밀번호가 올바르지 않습니다."),
    EMAIL_IS_DUPLICATED(-102, "이미 사용 중인 이메일 주소입니다."),
    USER_NOT_FOUND(-103, "사용자 정보를 찾을 수 없습니다."),
    /** Common (-900 ~ -999) */
    ILLEGAL_ARGUMENT(-900, "파라미터가 올바르지 않습니다."),
    FAILED_PROCEED(-999, "요청하신 서비스를 처리하지 못했습니다.")
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
