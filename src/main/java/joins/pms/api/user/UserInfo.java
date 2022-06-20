package joins.pms.api.user;

import joins.pms.core.ValueObject;
import lombok.Getter;

import java.util.UUID;

@Getter
@ValueObject
public class UserInfo {
    private final UUID id;
    private final String email;
    private final String name;
    private final UserRole role;
    private final UserStatus status;

    public UserInfo(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
        this.status = user.getStatus();
    }
}
