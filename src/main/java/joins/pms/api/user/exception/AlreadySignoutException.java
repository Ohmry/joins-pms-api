package joins.pms.api.user.exception;

public class AlreadySignoutException extends RuntimeException {
    public AlreadySignoutException () { super(); }
    public AlreadySignoutException (String message) { super(); }
    public AlreadySignoutException (Throwable cause) { super(cause); }
}
