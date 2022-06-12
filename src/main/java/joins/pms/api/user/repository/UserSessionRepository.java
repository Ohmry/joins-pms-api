package joins.pms.api.user.repository;

import joins.pms.api.user.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
}
