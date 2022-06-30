package joins.pms.api.user.model;

import joins.pms.api.user.domain.User;
import joins.pms.api.user.domain.UserRole;

public class UserSimpleInfo {
    public final Long id;
    public final String email;
    public final String name;
    public final UserRole role;

    protected UserSimpleInfo(Long id, String email, String name, UserRole role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public static UserSimpleInfo valueOf(User user) {
        return new UserSimpleInfo(user.getId(), user.getEmail(), user.getName(), user.getRole());
    }
}
