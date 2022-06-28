package joins.pms.api.exception;

public class DomainNotFoundException extends RuntimeException {
    public DomainNotFoundException() {
        super();
    }
    public DomainNotFoundException(Class<?> object) {
        super(object.getName());
    }
}
