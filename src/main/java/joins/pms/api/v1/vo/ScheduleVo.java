package joins.pms.api.v1.vo;

import joins.pms.api.v1.entity.Schedule;
import joins.pms.core.vo.BaseVo;
import lombok.Getter;

@Getter
public class ScheduleVo extends BaseVo {
    private final Long id;
    private final String name;

    public ScheduleVo (Schedule schedule) {
        super(schedule);
        this.id = schedule.getId();
        this.name = schedule.getName();
    }

    public static ScheduleVo of (Schedule schedule) {
        return new ScheduleVo(schedule);
    }
}
