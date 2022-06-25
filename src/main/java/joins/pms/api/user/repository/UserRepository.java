package joins.pms.api.user.repository;

import joins.pms.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCredentialEmail(String email);
    boolean existsByCredentialEmail(String email);
}
