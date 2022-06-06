package joins.pms.core.model.entity;

import joins.pms.core.model.code.RowStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Column(name = "ROW_STATUS", length = 1, nullable = false)
    public RowStatus status;

    @CreationTimestamp
    @Column(name = "CREATED_DT", updatable = false)
    public LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "UPDATED_DT")
    public LocalDateTime updatedTime;
}
