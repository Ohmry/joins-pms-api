package joins.pms.api.v1.tag.model;

import joins.pms.core.model.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TagDto extends BaseDto {
    private Long id;
    private String name;
}
