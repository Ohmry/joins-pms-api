package joins.pms.api.v1.project.model;

import joins.pms.api.domain.Progress;
import joins.pms.api.v1.project.domain.Project;
import joins.pms.core.annotations.ValueObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ValueObject
public class ProjectSimpleInfo {
    public final Long id;
    public final String title;
    public final String description;
    public final String startDate;
    public final String startDateHour;
    public final String startDateMinute;
    public final String endDate;
    public final String endDateHour;
    public final String endDateMinute;
    public final Progress progress;

    protected ProjectSimpleInfo(Long id, String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, Progress progress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.startDateHour = startDateTime.format(DateTimeFormatter.ofPattern("HH"));
        this.startDateMinute = startDateTime.format(DateTimeFormatter.ofPattern("mm"));
        this.endDate = endDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.endDateHour = endDateTime.format(DateTimeFormatter.ofPattern("HH"));
        this.endDateMinute = endDateTime.format(DateTimeFormatter.ofPattern("mm"));
        this.progress = progress;
    }

    public static ProjectSimpleInfo valueOf(Project project) {
        return new ProjectSimpleInfo(project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getStartDateTime(),
                project.getEndDateTime(),
                project.getProgress()
        );
    }
}
