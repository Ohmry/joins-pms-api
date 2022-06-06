package joins.pms.user.model.dto;

import joins.pms.core.model.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserSessionDto extends BaseDto {
    private UUID id;
    private String session;
    private LocalDateTime expireDateTime;
}
