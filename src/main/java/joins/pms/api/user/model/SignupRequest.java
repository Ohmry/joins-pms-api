package joins.pms.api.user.model;

import joins.pms.api.v1.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class SignupRequest {
    public String email;
    public String password;
    public String name;

    public void validate() {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password) || !StringUtils.hasText(name)) {
            throw new IllegalRequestException();
        }
    }
}
