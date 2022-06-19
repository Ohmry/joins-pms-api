package joins.pms.api.v1.common.model.converter;

import joins.pms.api.v1.common.model.Status;
import joins.pms.core.model.converter.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter extends EnumConverter<Status> {
    public StatusConverter () {
        super(Status.class);
    }
}
