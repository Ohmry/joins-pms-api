package joins.pms.api.user.model;

import joins.pms.core.domain.BaseDataTransferObject;
import org.springframework.util.StringUtils;

public class ResignRequest extends BaseDataTransferObject {
    public String refreshToken;

    @Override
    public BaseDataTransferObject checkParameterValidation() {
        if (!StringUtils.hasText(refreshToken)) {
            throw new IllegalArgumentException();
        }
        return this;
    }
}
