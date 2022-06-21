package joins.pms.api.v1.task;

import joins.pms.api.v1.status.Progress;
import joins.pms.core.BaseEntity;
import joins.pms.core.RowStatus;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Entity
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 8)
    private String startDe;

    @Column(length = 8)
    private String endDe;

    @Column
    private Progress progress;

    public Task (Long id, String name, String startDe, String endDe, Progress progress,
                 RowStatus rowStatus, LocalDateTime createdTime, LocalDateTime updatedTime) {
        super(rowStatus, createdTime, updatedTime);
        this.id = id;
        this.name = name;
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
