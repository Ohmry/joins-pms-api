package joins.pms.api.user.model;

import joins.pms.core.domain.BaseDataTransferObject;
import org.springframework.util.StringUtils;

public class UserUpdateRequest extends BaseDataTransferObject {
    public Long id;
    public String name;

    @Override
    public BaseDataTransferObject checkParameterValidation() {
        if (id < 1 || !StringUtils.hasText(name)) {
            throw new IllegalArgumentException();
        }
        return this;
    }

    @Override
    public BaseDataTransferObject equalsUserId(Long userId) {
        if (!this.id.equals(userId)) {
            throw new IllegalArgumentException();
        }
        return this;
    }
}
