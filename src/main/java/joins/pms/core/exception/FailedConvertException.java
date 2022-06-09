package joins.pms.core.exception;

public class FailedConvertException extends RuntimeException {
    public FailedConvertException () {
        super();
    }
    public FailedConvertException (String message) {
        super(message);
    }
}
