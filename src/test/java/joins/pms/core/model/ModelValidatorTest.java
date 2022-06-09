package joins.pms.core.model;

import joins.pms.api.v1.schedule.model.ScheduleDto;
import joins.pms.api.v1.schedule.model.Schedule;
import org.junit.jupiter.api.Test;

public class ModelValidatorTest {
    ModelValidator modelValidator;
    public ModelValidatorTest () {
        this.modelValidator = new ModelValidator();
    }
    @Test
    void checkAllFieldExists () {
        ScheduleDto scheduleDto = new ScheduleDto();
        modelValidator.existsFieldsWithoutPrimaryKey(scheduleDto, Schedule.class);
    }
}
