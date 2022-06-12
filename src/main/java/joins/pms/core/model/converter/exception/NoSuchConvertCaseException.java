package joins.pms.core.model.converter.exception;

public class NoSuchConvertCaseException extends RuntimeException {
    public NoSuchConvertCaseException () { super(); }
    public NoSuchConvertCaseException (String message) { super(message); }
    public NoSuchConvertCaseException (Throwable cause) { super(cause); }
}
