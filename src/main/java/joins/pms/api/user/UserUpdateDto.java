package joins.pms.api.user;

import joins.pms.core.RowStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class UserUpdateDto {
    private UUID id;
    private String email;
    private String name;
    private UserRole role;
    private UserStatus status;
}
