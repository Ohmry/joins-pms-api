package joins.pms.core.model.converter;

import joins.pms.core.model.RowStatus;

import javax.persistence.Converter;
@Converter(autoApply = true)
public class RowStatusConverter extends EnumConverter<RowStatus> {
    public RowStatusConverter () {
        super(RowStatus.class);
    }
}
