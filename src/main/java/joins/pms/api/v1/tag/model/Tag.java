package joins.pms.api.v1.tag.model;

import joins.pms.api.v1.schedule.model.Schedule;
import joins.pms.api.v1.task.model.Task;
import joins.pms.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID", nullable = false)
    private Long id;

    @Column(name = "TAG_NM")
    private String name;
}
