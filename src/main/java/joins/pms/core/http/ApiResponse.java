package joins.pms.core.http;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private int code;
    private String message;
    private int count;
    private Object data;

    public ApiResponse () {}
    public ApiResponse (ApiStatus apiStatus) {
        this.code = apiStatus.getCode();
        this.message = apiStatus.getMessage();
    }
    public ApiResponse (ApiStatus apiStatus, Object data) {
        this.code = apiStatus.getCode();
        this.message = apiStatus.getMessage();

        if (data == null) {
            this.count = 0;
        } else if (data instanceof List) {
            this.count = ((List<?>) data).size();
        } else {
            this.count = 1;
        }
        this.data = data;
    }
}
