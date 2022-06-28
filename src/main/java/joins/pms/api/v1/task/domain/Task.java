package joins.pms.api.v1.task.domain;

import joins.pms.core.domain.BaseEntity;
import joins.pms.core.domain.Progress;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column
    private String description;
    @Column
    private LocalDateTime startDateTime;
    @Column
    private LocalDateTime endDateTime;
    @Enumerated(EnumType.STRING)
    @Column
    private Progress progress;
}
