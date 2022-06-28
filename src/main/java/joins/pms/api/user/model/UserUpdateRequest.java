package joins.pms.api.user.model;

import joins.pms.api.v1.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class UserUpdateRequest {
    public Long id;
    public String name;

    public void validate() {
        if (id < 1 || !StringUtils.hasText(name)) {
            throw new IllegalRequestException();
        }
    }
}
