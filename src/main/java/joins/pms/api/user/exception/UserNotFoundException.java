package joins.pms.api.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException () { super(); }
    public UserNotFoundException (String message) { super(message); }
    public UserNotFoundException (Throwable cause) { super(cause); }
}
