package joins.pms.api.user.model;

import joins.pms.core.domain.BaseDataTransferObject;
import org.springframework.util.StringUtils;

public class SigninRequest extends BaseDataTransferObject {
    public String email;
    public String password;

    @Override
    public BaseDataTransferObject checkParameterValidation() {
        if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException();
        }
        return this;
    }
}
