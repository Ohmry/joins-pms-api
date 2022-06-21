package joins.pms.api.v1.schedule;

import joins.pms.api.v1.status.Progress;
import joins.pms.core.ValueObject;

@ValueObject
public class ScheduleInfo {
    private Long id;
    private String name;
    private String description;
    private String startDe;
    private String endDe;
    private Progress progress;

    public ScheduleInfo (Long id, String name, String description, String startDe, String endDe, Progress progress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDe = startDe;
        this.endDe = endDe;
        this.progress = progress;
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
