package joins.pms.core.api;

public enum ApiStatus {
    SUCCESS(0, "정상"),
    DATA_IS_EMPTY(-100, "데이터가 존재하지 않습니다.")
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
