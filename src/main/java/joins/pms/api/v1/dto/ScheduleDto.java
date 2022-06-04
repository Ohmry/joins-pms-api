package joins.pms.api.v1.dto;

import joins.pms.core.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ScheduleDto extends BaseDto {
    private Long id;
    private String name;

    public ScheduleDto() {
        super();
    }
    public ScheduleDto (Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}
