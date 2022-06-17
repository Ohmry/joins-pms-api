package joins.pms.api.v1.schedule.model;

import joins.pms.core.model.converter.IEnumConverter;

import java.util.EnumSet;

public enum ScheduleStatus implements IEnumConverter {
    READY("RDY"),
    PROCEED("PRD"),
    ABORT("ABT"),
    FINISHED("FND")
    ;

    private final String value;

    ScheduleStatus (String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
