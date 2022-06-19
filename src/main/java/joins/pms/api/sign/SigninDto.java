package joins.pms.api.sign;

import joins.pms.api.user.User;
import joins.pms.api.user.UserRole;
import lombok.Getter;

@Getter
public class SigninDto {
    private final String email;
    private final String name;
    private final UserRole role;

    public SigninDto (User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
    }
}
