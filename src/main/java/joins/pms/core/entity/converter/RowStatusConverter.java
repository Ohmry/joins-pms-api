package joins.pms.core.entity.converter;

import joins.pms.core.entity.RowStatus;

import javax.persistence.Converter;
@Converter(autoApply = true)
public class RowStatusConverter extends EnumConverter<RowStatus> {
    public RowStatusConverter () {
        super(RowStatus.class);
    }
}
