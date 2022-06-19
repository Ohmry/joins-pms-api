package joins.pms.api.v1.schedule;

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
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column
    private String description;

    @Column(length = 8)
    private String startDe;

    @Column(length = 8)
    private String endDe;

    @Column(nullable = false, length = 4)
    private Progress progress;
}
