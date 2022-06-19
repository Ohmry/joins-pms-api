package joins.pms.core;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@SuperBuilder
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
}
