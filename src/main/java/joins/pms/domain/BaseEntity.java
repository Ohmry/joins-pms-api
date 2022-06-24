package joins.pms.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @Column(nullable = false)
    protected RowStatus rowStatus;

    @Column(updatable = false)
    protected LocalDateTime createdTime;

    @Column
    protected LocalDateTime updatedTime;

    protected BaseEntity () {
        this.rowStatus = RowStatus.NORMAL;
    }

    protected BaseEntity (RowStatus rowStatus, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.rowStatus = rowStatus;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    @PrePersist
    public void prePersist () {
        this.rowStatus = RowStatus.NORMAL;
        this.createdTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate () {
        this.updatedTime = LocalDateTime.now();
    }
}
