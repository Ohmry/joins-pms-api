package joins.pms.api.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column
    protected RowStatus rowStatus;
    @Column
    private LocalDateTime createdTime;
    @Column
    private LocalDateTime updatedTime;
    @Transient
    private boolean isCreated;
    @Transient
    private boolean isUpdated;
    
    public BaseEntity() {
        this.rowStatus = RowStatus.NORMAL;
        this.createdTime = null;
        this.updatedTime = null;
    }
    
    public BaseEntity(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
        this.createdTime = null;
        this.updatedTime = null;
    }
    
    public boolean isCreated() {
        return this.isCreated;
    }
    
    public boolean isUpdated() {
        return this.isUpdated;
    }
    
    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }
    
    @PrePersist
    public void prePersist() {
        this.isCreated = false;
        this.createdTime = LocalDateTime.now();
    }
    
    @PostPersist
    public void postPersist() {
        this.isCreated = true;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.isUpdated = false;
        this.updatedTime = LocalDateTime.now();
    }
    
    @PostUpdate
    public void postUpdate() {
        this.isUpdated = true;
    }
}
