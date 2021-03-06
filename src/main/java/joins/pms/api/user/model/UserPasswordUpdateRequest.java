package joins.pms.api.user.model;

import joins.pms.api.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class UserPasswordUpdateRequest {
    public Long id;
    public String password;
    public String newPassword;
    
    public void validate() {
        if (id == null || id < 1 || !StringUtils.hasText(password) || !StringUtils.hasText(newPassword)) {
            throw new IllegalRequestException();
        }
    }
}
