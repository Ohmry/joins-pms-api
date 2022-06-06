package joins.pms.api.v1.repository;

import joins.pms.api.v1.model.entity.Schedule;
import joins.pms.core.model.code.RowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByStatusNot (RowStatus status);
    Optional<Schedule> findByIdAndStatusNot (Long id, RowStatus status);
}
