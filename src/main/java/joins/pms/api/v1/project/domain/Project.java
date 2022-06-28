package joins.pms.api.v1.project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import joins.pms.api.v1.board.domain.Board;
import joins.pms.core.domain.BaseEntity;
import joins.pms.core.domain.Progress;
import joins.pms.core.domain.RowStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private LocalDateTime startDateTime;
    @Column
    private LocalDateTime endDateTime;
    @Enumerated(EnumType.STRING)
    @Column
    private Progress progress;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private Board board;

    public Project() {
        super();
    }
    public Project(Board board, String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, Progress progress) {
        super();
        LocalDateTime now = LocalDateTime.now();
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime == null ? now : startDateTime;
        this.endDateTime = endDateTime == null ? now : endDateTime;
        this.progress = progress == null ? Progress.READY : progress;
        this.board = board;
    }

    public enum Field {
        title,
        description,
        startDateTime,
        endDateTime,
        progress,
        rowStatus,
        board
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
                this.startDateTime = (LocalDateTime) value;
                break;
            case endDateTime:
                this.endDateTime = (LocalDateTime) value;
                break;
            case progress:
                this.progress = Progress.valueOf(value.toString());
                break;
            case rowStatus:
                this.rowStatus = RowStatus.valueOf(value.toString());
                break;
            case board:
                this.board = (Board) value;
                break;
            default:
                break;
        }
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
        return this.startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public Progress getProgress() {
        return this.progress;
    }

    public Board getBoard() {
        return this.board;
    }
}
