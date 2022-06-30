package joins.pms.api.exception;

public class UnauthorizationException extends RuntimeException {
    public UnauthorizationException() {
        super();
    }
    public UnauthorizationException(String message) {
        super(message);
    }
    public UnauthorizationException(Throwable cause) {
        super(cause);
    }
}
