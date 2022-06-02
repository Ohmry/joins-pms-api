package joins.pms.core.entity.converter;

import joins.pms.core.entity.RowStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.EnumSet;
import java.util.NoSuchElementException;
@Converter(autoApply = true)
public class RowStatusConverter implements AttributeConverter<RowStatus, String> {
    @Override
    public String convertToDatabaseColumn(RowStatus attribute) {
        System.out.println(attribute.getValue());
        return attribute.getValue();
    }
    @Override
    public RowStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(RowStatus.class).stream()
                .filter(e -> e.getValue().equals(dbData))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }
}
