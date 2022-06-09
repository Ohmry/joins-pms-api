package joins.pms.api.user.model.converter;

import joins.pms.core.model.converter.EnumConverter;
import joins.pms.api.user.model.UserStatus;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter extends EnumConverter<UserStatus> {
    public UserStatusConverter () {
        super(UserStatus.class);
    }
}
