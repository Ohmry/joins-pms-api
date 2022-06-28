package joins.pms.api.user.model;

import joins.pms.api.exception.IllegalRequestException;
import joins.pms.api.user.domain.UserRole;
import org.springframework.util.StringUtils;


public class UserUpdateRequest {
    public Long id;
    public String name;
    public UserRole role;
    
    public void validate() {
        if (id == null || id < 1 || !StringUtils.hasText(name) || role == null) {
            throw new IllegalRequestException();
        }
    }
}
