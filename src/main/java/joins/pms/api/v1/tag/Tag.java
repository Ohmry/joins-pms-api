package joins.pms.api.v1.tag;

import joins.pms.core.BaseEntity;
import joins.pms.core.RowStatus;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Entity
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Tag (Long id, String name,
                RowStatus rowStatus, LocalDateTime createdTime, LocalDateTime updatedTime) {
        super(rowStatus, createdTime, updatedTime);
        this.id = id;
        this.name = name;
    }
}
