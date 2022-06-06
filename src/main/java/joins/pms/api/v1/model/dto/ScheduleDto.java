package joins.pms.api.v1.model.dto;

import joins.pms.core.model.dto.BaseDto;
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
