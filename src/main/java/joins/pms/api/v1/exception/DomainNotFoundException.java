package joins.pms.api.v1.exception;

public class DomainNotFoundException extends RuntimeException {
    public DomainNotFoundException() {
        super();
    }
    public DomainNotFoundException(Class<?> classObject) {
        super(classObject.getName());
    }
}
