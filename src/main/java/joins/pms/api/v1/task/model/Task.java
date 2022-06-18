package joins.pms.api.v1.task.model;

import joins.pms.api.user.model.User;
import joins.pms.api.v1.model.Status;
import joins.pms.api.v1.tag.model.Tag;
import joins.pms.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID", nullable = false)
    private Long id;

    @Column(name = "TASK_NM", nullable = false, length = 100)
    private String name;

    @Column(name = "START_DE", length = 8)
    private String startDe;

    @Column(name = "END_DE", length = 8)
    private String endDe;

    @Column(name = "STATUS", length = 4)
    private Status status;

    @Column(name = "PROGRESS")
    private Integer progress;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "TASK_USER",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "USR_ID"))
    private User owner;

    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USR_ID")
    private Set<User> manager;

    @OneToMany(targetEntity = Tag.class, fetch = FetchType.LAZY)
    private Set<Tag> tags;

}
