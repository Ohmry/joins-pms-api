package joins.pms.api.v1.schedule.service;

import joins.pms.api.v1.schedule.model.ScheduleDto;
import joins.pms.api.v1.schedule.model.Schedule;
import joins.pms.api.v1.schedule.repository.ScheduleRepository;
import joins.pms.core.model.converter.ModelConverter;
import joins.pms.core.model.RowStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ModelConverter modelConverter;

    public ScheduleService (ScheduleRepository scheduleRepository, ModelConverter modelConverter) {
        this.scheduleRepository = scheduleRepository;
        this.modelConverter = modelConverter;
    }

    public List<ScheduleDto> findAll () {
        List<Schedule> list = scheduleRepository.findAllByStatusNot(RowStatus.DELETED);
        return list.stream().map(schedule -> modelConverter.convert(schedule, ScheduleDto.class)).collect(Collectors.toList());
    }

    public ScheduleDto findById (Long id) {
        Optional<Schedule> schedule = scheduleRepository.findByIdAndStatusNot(id, RowStatus.DELETED);
        return schedule.map(value -> modelConverter.convert(value, ScheduleDto.class)).orElse(null);
    }

    public Long save (ScheduleDto scheduleDto) {
        Schedule schedule = modelConverter.convert(scheduleDto, Schedule.class);
        schedule = scheduleRepository.save(schedule);
        return schedule.getId();
    }

    public void delete (Long id) {
        scheduleRepository.deleteById(id);
    }
}
