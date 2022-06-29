package joins.pms.api.http;

/**
 * 어플리케이션의 응답에 대한 코드와 메세지를 관리하는 Enum 클래스.
 * ApiResponse 클래스에서 응답을 생성할 때, 주로 사용된다.
 *
 * @see ApiResponse
 * @version 1.0
 * @author lee.byunghoon
 */
public enum ApiStatus {
    SUCCESS(0, "정상"),
    /** User(100 ~ 199, -100 ~ -199) */
    USER_NEED_RESIGNIN(100, "비밀번호를 성공적으로 변경하였습니다. 로그인을 다시 시도해주세요"),
    BAD_CREDENTIAL(-100, "이메일 또는 비밀번호가 올바르지 않습니다."),
    /** Common (-900 ~ -999) */
    ILLEGAL_PARAMETERS(-900, "파라미터가 올바르지 않습니다."),
    DOMAIN_NOT_FOUND(-901, "정보를 찾을 수 없습니다."),
    UNAUTHORIZATION(-902, "인증받지 않은 요청입니다."),
    
    
    ;
    private final int code;
    private final String message;
    
    ApiStatus (int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public int getCode () { return this.code; }
    public String getMessage () { return this.message; }
}
