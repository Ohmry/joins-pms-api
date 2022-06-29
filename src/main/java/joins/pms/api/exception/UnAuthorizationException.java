package joins.pms.api.exception;

public class UnAuthorizationException extends RuntimeException {
    public UnAuthorizationException() {
        super();
    }
    public UnAuthorizationException(String requestUri) {
        super(requestUri);
    }
}
