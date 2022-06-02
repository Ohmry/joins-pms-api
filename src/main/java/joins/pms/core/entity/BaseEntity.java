package joins.pms.core.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @ColumnDefault("'A'")
    @Column(length = 1, nullable = false)
    protected String status;

    @CreationTimestamp
    @Column(updatable = false)
    protected LocalDateTime createdTime;

    @UpdateTimestamp
    @Column
    protected LocalDateTime updatedTime;
}
