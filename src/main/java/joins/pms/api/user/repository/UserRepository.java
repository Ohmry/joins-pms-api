package joins.pms.api.user.repository;

import joins.pms.api.domain.RowStatus;
import joins.pms.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndRowStatus(Long userId, RowStatus rowStatus);
    Optional<User> findByEmailAndRowStatus(String email, RowStatus rowStatus);
    List<User> findAllByRowStatus(RowStatus rowStatus);
    boolean existsByEmail(String email);
}
