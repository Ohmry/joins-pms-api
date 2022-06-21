package joins.pms.api.v1.schedule;

import joins.pms.api.v1.status.Progress;
import joins.pms.core.RowStatus;
import lombok.Data;

@Data
public class ScheduleDto {
    private Long id;
    private String name;
    private String description;
    private String startDe;
    private String endDe;
    private Progress progress;
    private RowStatus rowStatus;
}
