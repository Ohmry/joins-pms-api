package joins.pms.api.user.model;

import joins.pms.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "USR_ID", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "USR_EMAIL", nullable = false, length = 100, unique = true)
    private String email;
    @Column(name = "USR_PWD")
    private String password;
    @Column(name = "USR_NM", nullable = false, length = 50)
    private String name;
    @Column(name = "ROLE", nullable = false, length = 3)
    private UserRole userRole;
    @Column(name = "STATUS", nullable = false, length = 4)
    private UserStatus userStatus;
}
