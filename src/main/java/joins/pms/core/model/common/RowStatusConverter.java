package joins.pms.core.model.common;

import joins.pms.core.model.common.RowStatus;
import joins.pms.core.model.common.converter.EnumConverter;

import javax.persistence.Converter;
@Converter(autoApply = true)
public class RowStatusConverter extends EnumConverter<RowStatus> {
    public RowStatusConverter () {
        super(RowStatus.class);
    }
}
