package joins.pms.core.jwt.exception;

public class JwtTokenInvalidException extends RuntimeException {
    public JwtTokenInvalidException() {
        super();
    }
    public JwtTokenInvalidException(String message) {
        super(message);
    }
    public JwtTokenInvalidException(Throwable cause) {
        super(cause);
    }
}
