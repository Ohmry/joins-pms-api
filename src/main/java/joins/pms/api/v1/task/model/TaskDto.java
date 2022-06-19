package joins.pms.api.v1.task.model;

import joins.pms.api.v1.common.model.Status;
import joins.pms.api.v1.schedule.model.ScheduleDto;
import joins.pms.api.v1.tag.model.TagDto;
import joins.pms.core.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskDto extends BaseDto {
    private Long id;
    private String name;
    private String startDe;
    private String endDe;
    private Status status;
    private Integer progress;
    private ScheduleDto schedule;
    private Set<TagDto> tags;
}
