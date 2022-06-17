package joins.pms.api.v1.schedule.model.converter;

import joins.pms.api.v1.schedule.model.ScheduleStatus;
import joins.pms.core.model.converter.EnumConverter;

public class ScheduleStatusConverter extends EnumConverter<ScheduleStatus> {
    public ScheduleStatusConverter(Class<ScheduleStatus> enumClass) {
        super(enumClass);
    }
}
