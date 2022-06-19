package joins.pms.api.sign;

import joins.pms.api.user.User;
import joins.pms.api.user.UserRole;
import joins.pms.api.user.UserStatus;
import joins.pms.core.RowStatus;
import lombok.Data;

@Data
public class SignupDto {
    private String email;
    private String password;
    private String name;
    private UserRole role;
    private UserStatus status;
    private RowStatus rowStatus;

    public User toEntity () {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .role(this.role)
                .status(this.status)
                .rowStatus(this.rowStatus)
                .build();
    }
}
