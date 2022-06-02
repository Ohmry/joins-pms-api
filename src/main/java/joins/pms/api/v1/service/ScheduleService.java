package joins.pms.api.v1.service;

import joins.pms.api.v1.dto.ScheduleDto;
import joins.pms.api.v1.entity.Schedule;
import joins.pms.api.v1.repository.ScheduleRepository;
import joins.pms.api.v1.vo.ScheduleVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService (ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleVo> findAll () {
        return scheduleRepository.findAll().stream().map(ScheduleVo::of).collect(Collectors.toList());
    }

    public ScheduleVo findById (Long id) {
        Optional<Schedule> result = scheduleRepository.findById(id);
        return result.map(ScheduleVo::of).orElse(null);
    }

    public Long save (ScheduleDto scheduleDto) {
        Schedule schedule = scheduleRepository.save(Schedule.from(scheduleDto));
        return schedule.getId();
    }

    public void delete (Long id) {
        scheduleRepository.deleteById(id);
    }
}
