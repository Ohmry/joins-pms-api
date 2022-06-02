package joins.pms.api.v1.entity;

import joins.pms.api.v1.dto.ScheduleDto;
import joins.pms.core.entity.BaseEntity;
import joins.pms.core.entity.RowStatus;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    public Schedule () {}
    public static Schedule of (ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDto.getId());
        schedule.setName(scheduleDto.getName());
        schedule.setStatus(RowStatus.NORMAL);
        return schedule;
    }
}
