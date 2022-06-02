package joins.pms.api.v1.test;

import joins.pms.api.v1.dto.ScheduleDto;
import joins.pms.api.v1.service.ScheduleService;
import joins.pms.core.context.SpringContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DummyCreator {
    public void create (DummyType dummyType, int count) {
        if (dummyType == DummyType.Schedule) {
            this.createSchedules(count);
        }
    }

    private void createSchedules (int count) {
        ScheduleService scheduleService = SpringContext.getBean(ScheduleService.class);
        for (int index = 0; index < count; index++) {
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setName("스케줄 더미데이터 " + (index + 1));
            scheduleService.save(scheduleDto);
        }
        log.info("Created " + count + " schedule dummy data");
    }
}
