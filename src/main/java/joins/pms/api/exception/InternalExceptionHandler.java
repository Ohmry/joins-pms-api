package joins.pms.api.exception;

import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class InternalExceptionHandler implements ErrorController {
    @ExceptionHandler(IllegalRequestException.class)
    public ResponseEntity<ApiResponse> handleIllegalRequestException(IllegalRequestException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiResponse(ApiStatus.ILLEGAL_PARAMETERS));
    }
    
    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<ApiResponse> handleDomainNotFoundException(DomainNotFoundException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse(ApiStatus.DOMAIN_NOT_FOUND, e.getMessage()));
    }
    
    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialException(BadCredentialException e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ApiResponse(ApiStatus.BAD_CREDENTIAL));
    }
    
    @GetMapping("/error")
    public ResponseEntity<ApiResponse> handle (HttpServletRequest request) {
        int status = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
        return ResponseEntity.status(status).build();
    }
}