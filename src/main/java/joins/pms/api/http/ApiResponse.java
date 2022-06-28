package joins.pms.api.http;

public class ApiResponse {
    public final int code;
    public final String message;
    public final Object data;
    
    public ApiResponse (ApiStatus apiStatus) {
        this.code = apiStatus.getCode();
        this.message = apiStatus.getMessage();
        this.data = null;
    }
    public ApiResponse (ApiStatus apiStatus, Object data) {
        this.code = apiStatus.getCode();
        this.message = apiStatus.getMessage();
        this.data = data;
    }
}