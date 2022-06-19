package joins.pms.api.v1.schedule.model;

import joins.pms.api.v1.tag.model.TagDto;
import joins.pms.core.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScheduleTagsDto extends BaseDto {
    private Long id;
    private Schedule schedule;
    private Set<TagDto> tags;
}
