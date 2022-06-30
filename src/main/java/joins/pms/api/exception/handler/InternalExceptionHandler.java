package joins.pms.api.exception.handler;

import joins.pms.api.exception.BadCredentialException;
import joins.pms.api.exception.DomainNotFoundException;
import joins.pms.api.exception.IllegalRequestException;
import joins.pms.api.group.exception.GroupNameAlreadyExistsException;
import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import joins.pms.api.user.exception.UserEmailAlreadyExistsException;
import joins.pms.core.jwt.exception.JwtTokenExpiredException;
import joins.pms.core.jwt.exception.JwtTokenInvalidException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
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

    @ExceptionHandler(JwtTokenInvalidException.class)
    public ResponseEntity<ApiResponse> handleJwtTokenInvalidException(JwtTokenInvalidException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse(ApiStatus.JWT_TOKEN_INVALID));
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ApiResponse> handleJwtTokenExpiredException(JwtTokenExpiredException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse(ApiStatus.JWT_TOKEN_EXPIRED));
    }

    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(ApiStatus.USER_EMAIL_ALREADY_EXISTS));
    }

    @ExceptionHandler(GroupNameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleGroupNameAlreadyExistsException(GroupNameAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(ApiStatus.GROUP_NAME_ALREADY_EXISTS));
    }

    @RequestMapping("/error")
    public ResponseEntity<ApiResponse> handle (HttpServletRequest request) {
        Object requestUriObject = request.getAttribute("requestUri");
        Object apiStatusObject = request.getAttribute("apiStatus");
        
        String requestUri = requestUriObject == null ? "/error" : requestUriObject.toString();
        ApiStatus apiStatus = apiStatusObject == null ? ApiStatus.INVALID_PERMISSION : ApiStatus.valueOf(apiStatusObject.toString());

        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .header("Location", requestUri)
            .body(new ApiResponse(apiStatus));
    }
}