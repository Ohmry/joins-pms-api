package joins.pms.api.v1.project.repository;

import joins.pms.api.v1.project.domain.Project;
import joins.pms.core.domain.RowStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByBoardIdAndIdAndRowStatus(Long boardId, Long projectId, RowStatus rowStatus);
    List<Project> findAllByBoardIdAndRowStatus(Long boardId, RowStatus rowStatus, Pageable pageable);
}
