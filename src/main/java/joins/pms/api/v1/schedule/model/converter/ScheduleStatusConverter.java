package joins.pms.api.v1.schedule.model.converter;

import joins.pms.api.v1.model.Status;
import joins.pms.core.model.converter.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ScheduleStatusConverter extends EnumConverter<Status> {
    public ScheduleStatusConverter () {
        super(Status.class);
    }
}
