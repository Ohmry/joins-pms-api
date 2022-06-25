package joins.pms.core.domain;

import joins.pms.core.domain.RowStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected RowStatus rowStatus;
    @Column(updatable = false)
    protected LocalDateTime createdTime;
    @Column
    protected LocalDateTime updatedTime;
    @Transient
    private boolean isCreated;
    @Transient
    private boolean isUpdated;

    protected BaseEntity() {
        this.rowStatus = RowStatus.NORMAL;
    }

    protected BaseEntity(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }

    protected boolean isCreated() {
        return this.isCreated;
    }

    protected boolean isUpdated() {
        return this.isUpdated;
    }

    @PrePersist
    public void prePersist() {
        this.rowStatus = RowStatus.NORMAL;
        this.createdTime = LocalDateTime.now();
        this.isCreated = false;
    }

    @PostPersist
    public void postPersist() {
        this.isCreated = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedTime = LocalDateTime.now();
        this.isUpdated = false;
    }

    @PostUpdate
    public void postUpdate() {
        this.isUpdated = true;
    }
}
