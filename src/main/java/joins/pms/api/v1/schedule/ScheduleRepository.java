package joins.pms.api.v1.schedule;

import joins.pms.api.v1.schedule.Schedule;
import joins.pms.core.RowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByRowStatus (RowStatus status);
    Optional<Schedule> findByIdAndRowStatus (Long id, RowStatus status);
}
