package joins.pms.core.domain;

import java.io.Serializable;

public abstract class BaseDataTransferObject implements Serializable {
    public BaseDataTransferObject checkParameterValidation() {
        return this;
    }
    public BaseDataTransferObject equalsUserId(Long userId) {
        return this;
    }
}
