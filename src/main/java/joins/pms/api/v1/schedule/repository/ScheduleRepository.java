package joins.pms.api.v1.schedule.repository;

import joins.pms.api.v1.schedule.model.Schedule;
import joins.pms.core.model.RowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByRowStatusNot (RowStatus status);
    Optional<Schedule> findByIdAndRowStatusNot (Long id, RowStatus status);
}
