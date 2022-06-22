package joins.pms.api.v1.schedule;

import joins.pms.core.RowStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService (ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleInfo> findAll () {
        List<Schedule> list = scheduleRepository.findAllByRowStatus(RowStatus.NORMAL);
        return list.stream().map(ScheduleInfo::new).collect(Collectors.toList());
    }

    public ScheduleInfo findById (Long id) {
        Optional<Schedule> schedule = scheduleRepository.findByIdAndRowStatus(id, RowStatus.NORMAL);
        return schedule.map(ScheduleInfo::new).orElse(null);
    }

    public Long save (ScheduleDto scheduleDto) {
        return scheduleRepository.save(scheduleDto.toEntity()).getId();
    }

    public Long update (Long id, ScheduleDto scheduleUpdateDto) {
        Optional<Schedule> schedule = scheduleRepository.findByIdAndRowStatus(id, RowStatus.NORMAL);
        ScheduleDto scheduleDto = schedule.map(ScheduleDto::new).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        scheduleDto.setName(scheduleUpdateDto.getName());
        scheduleDto.setDescription(scheduleUpdateDto.getDescription());
        scheduleDto.setStartDe(scheduleUpdateDto.getStartDe());
        scheduleDto.setEndDe(scheduleUpdateDto.getEndDe());
        scheduleDto.setProgress(scheduleUpdateDto.getProgress());
        return scheduleRepository.save(scheduleDto.toEntity()).getId();
    }

    public void delete (Long id) {
        Optional<Schedule> schedule = scheduleRepository.findByIdAndRowStatus(id, RowStatus.NORMAL);
        ScheduleDto scheduleDto = schedule.map(ScheduleDto::new).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        scheduleDto.setRowStatus(RowStatus.DELETED);
        scheduleRepository.save(scheduleDto.toEntity());
    }
}
