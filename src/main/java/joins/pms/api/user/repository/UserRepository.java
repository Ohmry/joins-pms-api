package joins.pms.api.user.repository;

import joins.pms.api.user.model.User;
import joins.pms.core.model.RowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * 사용자(User) 관련 레파지토리
 *
 * @see joins.pms.api.user.service.UserService
 * @version 1.0
 * @author Ohmry
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail (String email);
    Optional<User> findByIdAndRowStatus (UUID id, RowStatus rowStatus);
}
