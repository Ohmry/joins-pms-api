package joins.pms.core.dto.converter;

import joins.pms.api.v1.model.entity.Schedule;
import joins.pms.core.model.code.RowStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ConverterTest {
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
