package joins.pms.api.v1.task.domain;

import joins.pms.api.domain.BaseEntity;
import joins.pms.api.domain.Progress;
import joins.pms.api.domain.RowStatus;
import joins.pms.api.v1.board.domain.Board;
import joins.pms.api.v1.domain.Duration;
import joins.pms.api.v1.project.domain.Project;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PM_TASK")
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column
    private String description;
    @Embedded
    private Duration duration;
    @Enumerated(EnumType.STRING)
    @Column
    private Progress progress;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    
    public enum Field {
        title,
        description,
        startDateTime,
        endDateTime,
        progress,
        rowStatus
    }
    
    public static Task create(Board board, Project project, String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, Progress progress) {
        LocalDateTime now = LocalDateTime.now();
        Task task = new Task();
        task.title = title;
        task.description = description;
        task.duration = new Duration(startDateTime, endDateTime);
        task.progress = progress == null ? Progress.READY : progress;
        task.project = project;
        task.board = board;
        return task;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public LocalDateTime getStartDateTime() {
        return this.duration.getStartDateTime();
    }
    
    public LocalDateTime getEndDateTime() {
        return this.duration.getEndDateTime();
    }
    
    public Progress getProgress() {
        return this.progress;
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    public Project getProject() {
        return this.project;
    }
    
    public void update(Field field, Object value) {
        switch (field) {
            case title:
                this.title = value.toString();
                break;
            case description:
                this.description = value.toString();
                break;
            case startDateTime:
                this.duration.update(Duration.Field.startDateTime, value);
                break;
            case endDateTime:
                this.duration.update(Duration.Field.endDateTime, value);
                break;
            case progress:
                this.progress = Progress.valueOf(value.toString());
                break;
            case rowStatus:
                this.rowStatus = RowStatus.valueOf(value.toString());
            default:
                break;
        }
    }
}
