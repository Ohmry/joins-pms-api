package joins.pms.api.v1.schedule;

import joins.pms.api.v1.status.Progress;
import joins.pms.core.ValueObject;

@ValueObject
public class ScheduleInfo {
    private final Long id;
    private final String name;
    private final String description;
    private final String startDe;
    private final String endDe;
    private final Progress progress;

    public ScheduleInfo (Long id, String name, String description, String startDe, String endDe, Progress progress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDe = startDe;
        this.endDe = endDe;
        this.progress = progress;
    }

    public ScheduleInfo (Schedule entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.startDe = entity.getStartDe();
        this.endDe = entity.getEndDe();
        this.progress = entity.getProgress();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDe() {
        return startDe;
    }

    public String getEndDe() {
        return endDe;
    }

    public Progress getProgress() {
        return progress;
    }
}
