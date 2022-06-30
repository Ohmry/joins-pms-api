package joins.pms.api.v1.task.model;

import joins.pms.api.domain.Progress;
import joins.pms.api.v1.task.domain.Task;
import joins.pms.core.annotations.ValueObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ValueObject
public class TaskSimpleInfo {
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


    protected TaskSimpleInfo(Long id, String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, Progress progress) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");

        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDateTime.format(dateFormatter);
        this.startDateHour = startDateTime.format(hourFormatter);
        this.startDateMinute = startDateTime.format(minuteFormatter);
        this.endDate = endDateTime.format(dateFormatter);
        this.endDateHour = endDateTime.format(hourFormatter);
        this.endDateMinute = endDateTime.format(minuteFormatter);
        this.progress = progress;
    }

    public static TaskSimpleInfo valueOf(Task task) {
        return new TaskSimpleInfo(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStartDateTime(),
                task.getEndDateTime(),
                task.getProgress()
        );
    }
}
