package joins.pms.core.model.converter;

public interface IEnumConverter<E extends Enum<E>> {
    String getValue();
    E getEnum(String value);
}
