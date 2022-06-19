package joins.pms.api.user;

import joins.pms.core.RowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * 사용자(User) 관련 레파지토리
 *
 * @see UserService
 * @version 1.0
 * @author Ohmry
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail (String email);
    Optional<User> findByIdAndRowStatus (UUID id, RowStatus rowStatus);
}
