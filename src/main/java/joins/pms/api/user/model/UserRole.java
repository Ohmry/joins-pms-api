package joins.pms.api.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import joins.pms.core.model.converter.IEnumConverter;

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
    @Override
    public Enum getEnum(String value) {
        return UserRole.valueOf(value);
    }
}
