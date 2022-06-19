package joins.pms.api.user;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserInfoDto {
    private final UUID id;
    private final String email;
    private final String name;
    private final UserRole role;
    private final UserStatus status;

    public UserInfoDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
        this.status = user.getStatus();
    }
}
