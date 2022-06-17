package joins.pms.api.v1.schedule.model;

import joins.pms.core.model.BaseEntity;
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

    @Column(name = "SCHDL_DESC")
    private String description;

    @Column(name = "START_DE", length = 8)
    private String startDe;

    @Column(name = "END_DE", length = 8)
    private String endDe;

    @Column(name = "STATUS", length = 4)
    private ScheduleStatus status;
}
