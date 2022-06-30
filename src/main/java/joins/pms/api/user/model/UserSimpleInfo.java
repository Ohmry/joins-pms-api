package joins.pms.api.user.model;

import joins.pms.api.user.domain.User;
import joins.pms.api.user.domain.UserRole;
import joins.pms.core.annotations.ValueObject;

@ValueObject
public class UserSimpleInfo {
    public final Long id;
    public final String email;
    public final String name;

    protected UserSimpleInfo(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static UserSimpleInfo valueOf(User user) {
        return new UserSimpleInfo(user.getId(), user.getEmail(), user.getName());
    }
}
