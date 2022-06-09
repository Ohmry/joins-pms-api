package joins.pms.api.v1.schedule.model;

import joins.pms.core.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScheduleDto extends BaseDto {
    private Long id;
    private String name;
    private String description;
    private String startDe;
    private String endDe;
}
