package joins.pms.api.group.model;

import joins.pms.api.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class GroupCreateRequest {
    public String name;

    public void validate() {
        if (!StringUtils.hasText(name)) {
            throw new IllegalRequestException();
        }
    }
}
