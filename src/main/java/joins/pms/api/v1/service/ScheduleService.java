package joins.pms.api.v1.service;

import joins.pms.api.v1.dto.ScheduleDto;
import joins.pms.api.v1.entity.Schedule;
import joins.pms.api.v1.repository.ScheduleRepository;
import joins.pms.core.model.ModelConverter;
import joins.pms.core.model.exception.FailedConvertException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService (ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleDto> findAll () throws FailedConvertException {
        List<Schedule> list = scheduleRepository.findAll();
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        for (Schedule schedule : list) {
            ScheduleDto scheduleDto = ModelConverter.convert(schedule, ScheduleDto.class);
            scheduleDtoList.add(scheduleDto);
        }
        return scheduleDtoList;
    }

    public ScheduleDto findById (Long id) throws FailedConvertException {
        Optional<Schedule> result = scheduleRepository.findById(id);
        return result.isPresent() ? ModelConverter.convert(result.get(), ScheduleDto.class) : null;
    }

    public Long save (ScheduleDto scheduleDto) {
        Schedule schedule = scheduleRepository.save(Schedule.of(scheduleDto));
        return schedule.getId();
    }

    public void delete (Long id) {
        scheduleRepository.deleteById(id);
    }
}
