package joins.pms.api.v1.model.entity;

import joins.pms.api.v1.model.dto.ScheduleDto;
import joins.pms.core.model.entity.BaseEntity;
import joins.pms.core.model.common.RowStatus;
import lombok.*;

import javax.persistence.*;

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
}
