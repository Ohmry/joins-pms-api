package joins.pms.api.group.model;

import joins.pms.api.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class GroupUpdateRequest {
    public Long id;
    public String name;

    public void validate() {
        if (id == null || id < 1 || !StringUtils.hasText(name)) {
            throw new IllegalRequestException();
        }
    }
}
