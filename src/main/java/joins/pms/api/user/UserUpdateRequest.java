package joins.pms.api.user;

import joins.pms.core.DataTransferObject;
import lombok.Data;

import java.util.UUID;

@Data
@DataTransferObject
public class UserUpdateRequest {
    private UUID id;
    private String email;
    private String name;
    private UserRole role;
    private UserStatus status;
}
