package joins.pms.api.user.repository;

import joins.pms.api.user.model.User;
import joins.pms.core.model.RowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail (String email);
    Optional<User> findByIdAndRowStatus (UUID id, RowStatus rowStatus);
}
