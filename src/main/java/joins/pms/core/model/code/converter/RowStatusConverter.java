package joins.pms.core.model.code.converter;

import joins.pms.core.model.code.RowStatus;
import joins.pms.core.model.code.converter.EnumConverter;

import javax.persistence.Converter;
@Converter(autoApply = true)
public class RowStatusConverter extends EnumConverter<RowStatus> {
    public RowStatusConverter () {
        super(RowStatus.class);
    }
}
