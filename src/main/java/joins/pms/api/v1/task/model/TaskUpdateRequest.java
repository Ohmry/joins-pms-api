package joins.pms.api.v1.task.model;

import joins.pms.core.domain.Progress;

import java.time.LocalDateTime;

public class TaskUpdateRequest {
    public Long id;
    public String title;
    public String description;
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
    public Progress progress;
}
