package joins.pms.api.user.model;

import joins.pms.api.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class UserPasswordUpdateRequest {
    public String email;
    public String password;
    public String newPassword;
    
    public void validate() {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password) || !StringUtils.hasText(newPassword)) {
            throw new IllegalRequestException();
        }
    }
}
