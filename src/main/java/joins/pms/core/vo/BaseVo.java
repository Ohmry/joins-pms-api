package joins.pms.core.vo;

import joins.pms.core.entity.BaseEntity;
import joins.pms.core.entity.RowStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseVo {
    protected final RowStatus status;
    protected final LocalDateTime createdTime;
    protected final LocalDateTime updatedTime;

    protected BaseVo(BaseEntity baseEntity) {
        this.status = baseEntity.getStatus();
        this.createdTime = baseEntity.getCreatedTime();
        this.updatedTime = baseEntity.getUpdatedTime();
    }
}
