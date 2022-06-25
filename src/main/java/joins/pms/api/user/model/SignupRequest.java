package joins.pms.api.user.model;

import joins.pms.core.domain.BaseDataTransferObject;
import org.springframework.util.StringUtils;

public class SignupRequest extends BaseDataTransferObject {
    public String email;
    public String password;
    public String name;

    @Override
    public BaseDataTransferObject checkParameterValidation() {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password) || !StringUtils.hasText(name)) {
            throw new IllegalArgumentException();
        }
        return this;
    }
}
