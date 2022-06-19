package joins.pms.api.user;

import joins.pms.core.RowStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String email;
    private String password;
    private String name;
    private UserRole role;
    private UserStatus status;
    private RowStatus rowStatus;

    public User toEntity () {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .role(this.role)
                .status(this.status)
                .rowStatus(this.rowStatus)
                .build();
    }

    public UserDto (User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.rowStatus = user.getRowStatus();
    }
}
