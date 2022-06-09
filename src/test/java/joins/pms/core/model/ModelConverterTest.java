package joins.pms.core.model;

import joins.pms.api.v1.schedule.model.Schedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ModelConverterTest {
    @Test
    void DtoConverter_테스트 () {
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setName("테스트");
        schedule.setStatus(RowStatus.NORMAL);
        schedule.setCreatedTime(LocalDateTime.now());
        schedule.setUpdatedTime(LocalDateTime.now());
    }
}
