package joins.pms.api.user.exception;

public class AlreadyEmailExistsException extends RuntimeException {
    public AlreadyEmailExistsException() {
        super();
    }
    public AlreadyEmailExistsException(String message) {
        super(message);
    }
    public AlreadyEmailExistsException(Throwable cause) {
        super(cause);
    }
}
