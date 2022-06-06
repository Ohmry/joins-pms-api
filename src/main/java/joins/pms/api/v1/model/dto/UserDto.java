package joins.pms.api.v1.model.dto;

import joins.pms.core.model.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseDto {
    private Long id;
    private String email;
    private String password;
    private String name;
}
