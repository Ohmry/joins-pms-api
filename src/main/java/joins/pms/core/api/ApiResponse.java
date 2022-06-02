package joins.pms.core.api;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class ApiResponse {
    private final int status;
    private final String message;
    private final int count;
    private final Object data;

    public ApiResponse (HttpStatus httpStatus) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.count = 0;
        this.data = null;
    }

    public ApiResponse (HttpStatus httpStatus, Object data) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();

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
