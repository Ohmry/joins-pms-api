package joins.pms.core.model;

import joins.pms.core.model.RowStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseDto {
    public RowStatus rowStatus;
    public LocalDateTime createdTime;
    public LocalDateTime updatedTime;

    public BaseDto () {
        this.rowStatus = RowStatus.NORMAL;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }
}
