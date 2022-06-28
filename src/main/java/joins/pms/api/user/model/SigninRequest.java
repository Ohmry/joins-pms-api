package joins.pms.api.user.model;

import joins.pms.api.v1.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class SigninRequest {
    public String email;
    public String password;

    public void validate() {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            throw new IllegalRequestException();
        }
    }
}
