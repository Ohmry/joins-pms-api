package joins.pms.api.group.model;

import joins.pms.api.exception.IllegalRequestException;

public class GroupUserCreateRequest {
    public Long userId;

    public void validate() {
        if (userId == null || userId < 1) {
            throw new IllegalRequestException();
        }
    }
}
