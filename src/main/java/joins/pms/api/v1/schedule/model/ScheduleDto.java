package joins.pms.api.v1.schedule.model;

import joins.pms.api.v1.common.model.Status;
import joins.pms.api.v1.tag.model.TagDto;
import joins.pms.api.v1.task.model.TaskDto;
import joins.pms.core.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScheduleDto extends BaseDto {
    private Long id;
    private String name;
    private String description;
    private String startDe;
    private String endDe;
    private Status status;
    private Integer progress;
    private List<TaskDto> tasks;
    private Set<TagDto> tags;
}
