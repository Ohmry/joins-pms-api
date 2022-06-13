package joins.pms.api.user.exception;

public class LockedUserException extends RuntimeException {
    public LockedUserException () { super(); }
    public LockedUserException (String message) { super(message); }
    public LockedUserException (Throwable cause) { super(cause); }
}
