package joins.pms.api.v1.task.domain;

import joins.pms.api.domain.Progress;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskInfo {
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
    public final Long boardId;
    public final String boardName;
    public final Long projectId;
    public final String projectName;
    
    
    protected TaskInfo(Long id, String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, Progress progress,
                       Long boardId, String boardName, Long projectId, String projectName) {
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
        this.boardId = boardId;
        this.boardName = boardName;
        this.projectId = projectId;
        this.projectName = projectName;
    }
    
    public static TaskInfo valueOf(Task task) {
        return new TaskInfo(task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStartDateTime(),
            task.getEndDateTime(),
            task.getProgress(),
            task.getBoard().getId(),
            task.getBoard().getTitle(),
            task.getProject().getId(),
            task.getProject().getTitle()
        );
    }
}
