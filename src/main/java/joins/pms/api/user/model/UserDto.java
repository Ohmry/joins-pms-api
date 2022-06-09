package joins.pms.api.user.model;

import joins.pms.core.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseDto {
    private UUID id;
    private String email;
    private String password;
    private String name;
    private UserRole userRole;
    private UserStatus userStatus;
}
