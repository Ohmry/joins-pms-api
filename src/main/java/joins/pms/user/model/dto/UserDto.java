package joins.pms.user.model.dto;

import joins.pms.user.model.code.UserRole;
import joins.pms.user.model.code.UserStatus;
import joins.pms.core.model.dto.BaseDto;
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
