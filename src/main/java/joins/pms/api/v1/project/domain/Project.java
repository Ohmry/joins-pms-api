package joins.pms.api.v1.project.domain;

import joins.pms.api.domain.BaseEntity;
import joins.pms.api.domain.Progress;
import joins.pms.api.domain.RowStatus;
import joins.pms.api.v1.board.domain.Board;
import joins.pms.api.v1.domain.Duration;
import joins.pms.api.v1.task.domain.Task;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "TB_PM_PROJECT")
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column
    private String description;
    @Embedded
    Duration duration;
    @Enumerated(EnumType.STRING)
    @Column
    private Progress progress;
    @ManyToOne(targetEntity = Board.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    @OneToMany(mappedBy = "project")
    private Set<Task> tasks;
    
    public enum Field {
        title,
        description,
        startDateTime,
        endDateTime,
        progress,
        rowStatus
    }
    
    public static Project create(Board board, String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, Progress progress) {
        LocalDateTime now = LocalDateTime.now();
        Project project = new Project();
        project.title = title;
        project.description = description;
        project.duration = new Duration(startDateTime, endDateTime);
        project.progress = progress == null ? Progress.READY : progress;
        project.board = board;
        return project;
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
