package joins.pms.core.model.converter.exception;

public class ConvertFailedException extends RuntimeException {
    public ConvertFailedException () { super(); }
    public ConvertFailedException (String message) { super(message); }
    public ConvertFailedException (Throwable cause) { super(cause); }
}
