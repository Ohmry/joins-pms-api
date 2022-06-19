package joins.pms.exception;

public class IllegalTargetClassException extends RuntimeException {
    public IllegalTargetClassException () { super(); }
    public IllegalTargetClassException (String message) { super(message); }
    public IllegalTargetClassException (Throwable cause) { super(cause); }
}
