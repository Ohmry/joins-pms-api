package joins.pms.core;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Column(name = "ROW_STATUS", length = 1, nullable = false)
    public RowStatus rowStatus;

    @CreationTimestamp
    @Column(name = "CREATED_DT", updatable = false)
    public LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "UPDATED_DT")
    public LocalDateTime updatedTime;

    public BaseEntity (RowStatus rowStatus, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.rowStatus = rowStatus;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
