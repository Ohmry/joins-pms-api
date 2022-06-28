package joins.pms.api.v1.board.repository;

import joins.pms.api.domain.RowStatus;
import joins.pms.api.v1.board.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByIdAndRowStatus(Long id, RowStatus rowStatus);
    List<Board> findAllByRowStatus(RowStatus rowStatus, Pageable pageable);
}
