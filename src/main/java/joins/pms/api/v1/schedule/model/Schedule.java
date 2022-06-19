package joins.pms.api.v1.schedule.model;

import joins.pms.api.v1.common.model.Status;
import joins.pms.api.v1.tag.model.Tag;
import joins.pms.api.v1.task.model.Task;
import joins.pms.core.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHDL_ID", nullable = false)
    private Long id;

    @Column(name = "SCHDL_NM", nullable = false, length = 100)
    private String name;

    @Column(name = "SCHDL_DESC")
    private String description;

    @Column(name = "START_DE", length = 8)
    private String startDe;

    @Column(name = "END_DE", length = 8)
    private String endDe;

    @Column(name = "STATUS", nullable = false, length = 4)
    private Status status;

    @Column(name = "PROGRESS")
    private Integer progress;

    @OneToMany(targetEntity = Task.class, fetch = FetchType.LAZY, mappedBy = "schedule", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Task> tasks;

    @OneToMany(orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Set<Tag> tags;
}
