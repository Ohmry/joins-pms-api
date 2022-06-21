package joins.pms.api.sign;

import joins.pms.api.user.User;
import joins.pms.api.user.UserRole;
import joins.pms.api.user.UserStatus;
import joins.pms.core.DataTransferObject;
import joins.pms.core.RowStatus;
import lombok.Data;

@Data
@DataTransferObject
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private UserRole role;
    private UserStatus status;
    private RowStatus rowStatus;

    public User toEntity () {
        return new User(null, email, password, name, role, status, rowStatus, null, null);
    }
}
