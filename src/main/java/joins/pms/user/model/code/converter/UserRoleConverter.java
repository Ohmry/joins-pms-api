package joins.pms.user.model.code.converter;

import joins.pms.user.model.code.UserRole;
import joins.pms.core.model.code.converter.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter extends EnumConverter<UserRole> {
    public UserRoleConverter () {
        super(UserRole.class);
    }
}