package joins.pms.api.user.exception;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException() {
        super();
    }
    public InvalidCredentialException(String message) {
        super(message);
    }
    public InvalidCredentialException(Throwable cause) {
        super(cause);
    }
}
