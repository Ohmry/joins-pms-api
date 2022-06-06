package joins.pms.user.model.code;

import joins.pms.core.model.code.converter.IEnumConverter;

public enum UserStatus implements IEnumConverter {
    ACTIVATED("ACTV"),
    LOCKED("LOCK")
    ;
    private final String value;
    UserStatus (String value) {
        this.value = value;
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
