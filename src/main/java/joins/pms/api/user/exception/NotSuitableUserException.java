package joins.pms.api.user.exception;

import org.aspectj.weaver.ast.Not;

public class NotSuitableUserException extends RuntimeException {
    public NotSuitableUserException () { super(); }
    public NotSuitableUserException (String message) { super(message); }
    public NotSuitableUserException (Throwable cause) { super(cause); }
}
