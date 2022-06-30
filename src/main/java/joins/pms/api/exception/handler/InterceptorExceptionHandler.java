package joins.pms.api.exception.handler;

import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterceptorExceptionHandler {
    @GetMapping("/unauthorized")
    public ResponseEntity<ApiResponse> handleUnauthorizeException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse(ApiStatus.UNAUTHORIZED));
    }

}
