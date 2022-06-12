package joins.pms.api.user.model.converter;

import joins.pms.api.user.model.UserRole;
import joins.pms.core.model.converter.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter extends EnumConverter<UserRole> {
    public UserRoleConverter () {
        super(UserRole.class);
    }
}