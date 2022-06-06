package joins.pms.api.v1.model.code.converter;

import joins.pms.api.v1.model.code.UserRole;
import joins.pms.core.model.code.converter.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter extends EnumConverter<UserRole> {
    public UserRoleConverter () {
        super(UserRole.class);
    }
}