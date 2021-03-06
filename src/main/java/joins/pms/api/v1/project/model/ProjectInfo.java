package joins.pms.api.v1.project.model;

import joins.pms.api.domain.Progress;
import joins.pms.api.v1.board.model.BoardSimpleInfo;
import joins.pms.api.v1.project.domain.Project;
import joins.pms.api.v1.task.model.TaskSimpleInfo;
import joins.pms.core.annotations.ValueObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@ValueObject
public class ProjectInfo {
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
    public final BoardSimpleInfo board;
    public final Set<TaskSimpleInfo> tasks;
    
    protected ProjectInfo(Long id, String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, Progress progress,
                          BoardSimpleInfo board, Set<TaskSimpleInfo> tasks) {
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
        this.board = board;
        this.tasks = tasks;
    }
    
    public static ProjectInfo valueOf(Project project) {
        Set<TaskSimpleInfo> taskList = project.getTasks().stream()
                .map(TaskSimpleInfo::valueOf)
                .collect(Collectors.toSet());
        return new ProjectInfo(project.getId(),
            project.getTitle(),
            project.getDescription(),
            project.getStartDateTime(),
            project.getEndDateTime(),
            project.getProgress(),
            BoardSimpleInfo.valueOf(project.getBoard()),
            taskList
        );
    }
}
