package joins.pms.api.v1.task.repository;

import joins.pms.api.domain.RowStatus;
import joins.pms.api.v1.task.domain.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByBoardIdAndBoardRowStatusAndProjectIdAndProjectRowStatusAndIdAndRowStatus(Long boardId, RowStatus boardRowStatus, Long projectId, RowStatus projectRowStatus, Long taskId, RowStatus rowStatus);
    List<Task> findAllByBoardIdAndBoardRowStatusAndProjectIdAndProjectRowStatusAndRowStatus(Long boardId, RowStatus boardRowStatus, Long projectId, RowStatus projectRowStatus, RowStatus rowStatus, Pageable pageable);
}
