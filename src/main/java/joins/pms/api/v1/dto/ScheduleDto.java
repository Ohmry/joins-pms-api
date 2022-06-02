package joins.pms.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ScheduleDto {
    private Long id;
    private String name;

    public ScheduleDto() {}
    public ScheduleDto (Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public ScheduleDto (Long id, String name, String status) {

    }
}
