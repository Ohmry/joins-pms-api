package joins.pms.api.user.model;

import joins.pms.core.model.converter.IEnumConverter;

public enum UserStatus implements IEnumConverter<UserStatus> {
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
