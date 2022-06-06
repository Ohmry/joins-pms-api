package joins.pms.core.model.code;

import joins.pms.core.model.code.converter.EnumConverter;

import javax.persistence.Converter;
@Converter(autoApply = true)
public class RowStatusConverter extends EnumConverter<RowStatus> {
    public RowStatusConverter () {
        super(RowStatus.class);
    }
}
