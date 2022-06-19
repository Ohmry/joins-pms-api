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

    public ScheduleDto (Schedule schedule) {
        this.id = schedule.getId();
        this.name = schedule.getName();
        this.description = schedule.getDescription();
        this.startDe = schedule.getStartDe();
        this.endDe = schedule.getEndDe();
        this.progress = schedule.getProgress();
        this.rowStatus = schedule.getRowStatus();
    }

    public Schedule toEntity () {
        return Schedule.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .startDe(this.startDe)
                .endDe(this.endDe)
                .progress(this.progress)
                .rowStatus(this.rowStatus)
                .build();
    }
}
