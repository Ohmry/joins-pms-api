package joins.pms.api.v1.task;

import joins.pms.api.v1.status.Progress;
import joins.pms.core.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Getter
@Entity
@EqualsAndHashCode(callSuper = false)
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
}
