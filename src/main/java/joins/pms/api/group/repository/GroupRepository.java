package joins.pms.api.group.repository;

import joins.pms.api.domain.RowStatus;
import joins.pms.api.group.domain.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByIdAndRowStatus(Long boardId, RowStatus rowStatus);
    List<Group> findAllByRowStatus(RowStatus rowStatus, Pageable pageable);
    boolean existsByName(String name);
}
