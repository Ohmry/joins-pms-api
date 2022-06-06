package joins.pms.api.v1.model.code;

import joins.pms.core.model.code.converter.IEnumConverter;

public enum UserRole implements IEnumConverter {
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
