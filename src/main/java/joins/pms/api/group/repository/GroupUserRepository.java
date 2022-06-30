package joins.pms.api.group.repository;

import joins.pms.api.group.domain.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
    Optional<GroupUser> findByGroupIdAndUserId(Long groupId, Long userId);
}
