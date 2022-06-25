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
    public BaseDataTransferObject checkSameUser(Long id) {
        if (!this.id.equals(id)) {
            throw new IllegalArgumentException();
        }
        return this;
    }
}
