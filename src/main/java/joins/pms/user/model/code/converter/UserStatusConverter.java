package joins.pms.user.model.code.converter;

import joins.pms.core.model.code.converter.EnumConverter;
import joins.pms.user.model.code.UserStatus;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter extends EnumConverter<UserStatus> {
    public UserStatusConverter () {
        super(UserStatus.class);
    }
}
