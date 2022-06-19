package joins.pms.api.v1.schedule.model;

import joins.pms.api.v1.tag.model.Tag;
import joins.pms.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class ScheduleTags extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = Schedule.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHDL_ID")
    private Schedule schedule;

    @OneToMany(targetEntity = Tag.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Set<Tag> tags;
}
