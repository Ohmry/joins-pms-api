package joins.pms.api.user.model;

import joins.pms.core.model.converter.IEnumConverter;

public enum UserRole implements IEnumConverter<UserRole> {
    USER("USR"),
    ADMIN("ADM")
    ;
    private final String value;
    UserRole (String value) {
        this.value = value;
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
