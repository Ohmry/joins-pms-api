package joins.pms.core.dto;

import joins.pms.core.entity.RowStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseDto {
    public RowStatus status;
    public LocalDateTime createdTime;
    public LocalDateTime updatedTime;

    public BaseDto () {
        this.status = RowStatus.NORMAL;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }
}
