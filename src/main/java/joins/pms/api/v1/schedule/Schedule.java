package joins.pms.api.v1.schedule;

import joins.pms.api.v1.status.Progress;
import joins.pms.core.BaseEntity;
import joins.pms.core.RowStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Entity
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column
    private String description;

    @Column(length = 8)
    private String startDe;

    @Column(length = 8)
    private String endDe;

    @Column(nullable = false, length = 4)
    private Progress progress;

    public Schedule (Long id, String name, String description, String startDe, String endDe, Progress progress,
                     RowStatus rowStatus, LocalDateTime createdTime, LocalDateTime updatedTime) {
        super(rowStatus, createdTime, updatedTime);
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDe = startDe;
        this.endDe = endDe;
        this.progress = progress;
    }

    public Schedule (Long id, String name, String description, String startDe, String endDe, Progress progress, RowStatus rowStatus) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDe = startDe;
        this.endDe = endDe;
        this.progress = progress;
        this.rowStatus = rowStatus;
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
