package joins.pms.core.exception;

import joins.pms.api.user.exception.AlreadyEmailExistsException;
import joins.pms.api.user.exception.UserNotFoundException;
import joins.pms.core.http.ApiResponse;
import joins.pms.core.http.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InternalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(new ApiResponse(ApiStatus.ILLEGAL_ARGUMENT));
    }
    @ExceptionHandler(AlreadyEmailExistsException.class)
    public ResponseEntity<ApiResponse> handleAlreadyEmailExistsException(AlreadyEmailExistsException e) {
        return ResponseEntity
                .badRequest()
                .body(new ApiResponse(ApiStatus.EMAIL_IS_DUPLICATED, e.getMessage()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(ApiStatus.USER_NOT_FOUND));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(ApiStatus.FAILED_LOGIN));
    }
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse> handleExcpetion(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .internalServerError()
                .body(new ApiResponse(ApiStatus.FAILED_PROCEED));
    }
}
