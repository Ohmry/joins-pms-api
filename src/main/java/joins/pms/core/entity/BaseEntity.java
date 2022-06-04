package joins.pms.core.entity;

import joins.pms.core.entity.converter.RowStatusConverter;
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
    @Column(length = 1, nullable = false)
    public RowStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    public LocalDateTime createdTime;

    @UpdateTimestamp
    @Column
    public LocalDateTime updatedTime;
}
