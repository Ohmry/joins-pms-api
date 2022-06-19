package joins.pms.core;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

public abstract class EnumConverter<E extends Enum<E> & IEnumConverter> implements AttributeConverter<E, String> {
    private final Class<E> enumClass;
    public EnumConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }
    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute.getValue();
    }
    @Override
    public E convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(enumClass).stream()
                .filter(e -> e.getValue().equals(dbData))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }
}
