package joins.pms.core.model;

import joins.pms.api.v1.schedule.model.Schedule;
import joins.pms.api.v1.schedule.model.ScheduleDto;
import joins.pms.api.v1.model.Status;
import joins.pms.core.model.converter.ModelConverter;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ModelConverterTest {
    @Test
    void TypeValidate () {
        ModelConverter modelConverter = new ModelConverter();
        // Dto -> Entity
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setName("테스트");
        scheduleDto.setDescription("모델 변환 테스트");
        scheduleDto.setStatus(Status.READY);
        Schedule entity = modelConverter.convert(scheduleDto, Schedule.class);
        System.out.println(entity);
        // Entity -> Dto
        scheduleDto = modelConverter.convert(entity, ScheduleDto.class);
        System.out.println(scheduleDto);
        // Dto -> Map
        Map<String, Object> scheduleMap = modelConverter.convert(scheduleDto, Map.class);
        System.out.println(scheduleMap);
        // Map -> Dto
        scheduleDto = modelConverter.convert(scheduleMap, ScheduleDto.class);
        System.out.println(scheduleDto);
    }
}
