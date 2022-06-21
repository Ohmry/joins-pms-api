package joins.pms.api.sign;

import joins.pms.api.user.User;
import joins.pms.api.user.UserRole;
import joins.pms.core.ValueObject;
import lombok.Getter;

@ValueObject
public class SigninResponse {
    private final String email;
    private final String name;
    private final UserRole role;

    public SigninResponse(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.role = user.getRole();
    }

    public String getEmail () { return this.email; }
    public String getName () { return this.name; }
    public UserRole getRole () { return this.role; }
}