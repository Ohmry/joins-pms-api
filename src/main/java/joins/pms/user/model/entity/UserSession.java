package joins.pms.user.model.entity;

import joins.pms.core.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class UserSession extends BaseEntity {
    @Id
    @Column(name = "USR_ID", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "SESSION", nullable = false)
    private String session;
    @Column(name ="EXPIR_DT", nullable = false)
    private LocalDateTime expireDateTime;
}
