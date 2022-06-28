package joins.pms.api.v1.project.repository;

import joins.pms.api.domain.RowStatus;
import joins.pms.api.v1.project.domain.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByBoardIdAndBoardRowStatusAndIdAndRowStatus(Long boardId, RowStatus boardRowStatus, Long id, RowStatus rowStatus);
    List<Project> findAllByBoardIdAndBoardRowStatusAndRowStatus(Long boardId, RowStatus boardRowStatus, RowStatus rowStatus, Pageable pageable);
}
