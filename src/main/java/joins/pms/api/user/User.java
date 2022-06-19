package joins.pms.api.user;

import joins.pms.core.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@SuperBuilder
@Getter
@Entity
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    @Column
    private String password;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 3)
    private UserRole role;
    @Column(nullable = false, length = 4)
    private UserStatus status;
}
