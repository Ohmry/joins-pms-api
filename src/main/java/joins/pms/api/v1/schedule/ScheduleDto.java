package joins.pms.api.v1.schedule;

import joins.pms.api.v1.status.Progress;
import joins.pms.core.DataTransferObject;
import joins.pms.core.RowStatus;
import lombok.Data;

@DataTransferObject
public class ScheduleDto {
    private Long id;
    private String name;
    private String description;
    private String startDe;
    private String endDe;
    private Progress progress;
    private RowStatus rowStatus;

    public ScheduleDto (Schedule entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.startDe = entity.getStartDe();
        this.endDe = entity.getEndDe();
        this.progress = entity.getProgress();
        this.rowStatus = entity.getRowStatus();
    }

    public Schedule toEntity () {
        return new Schedule(this.id, this.name, this.description, this.startDe, this.endDe, this.progress, this.rowStatus);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDe() {
        return startDe;
    }

    public void setStartDe(String startDe) {
        this.startDe = startDe;
    }

    public String getEndDe() {
        return endDe;
    }

    public void setEndDe(String endDe) {
        this.endDe = endDe;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public RowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }
}
