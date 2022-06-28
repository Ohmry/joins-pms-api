package joins.pms.core.jwt.exception;

public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException() {
        super();
    }
    
    public JwtTokenExpiredException(String message) {
        super(message);
    }
    
    public JwtTokenExpiredException(Throwable cause) {
        super(cause);
    }
}
