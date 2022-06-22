package joins.pms.api.user;

import joins.pms.core.BaseEntity;
import joins.pms.core.RowStatus;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Entity
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

    public User (UUID id, String email, String password, String name, UserRole userRole, UserStatus userStatus,
                 RowStatus rowStatus, LocalDateTime createdTime, LocalDateTime updatedTime) {
        super(rowStatus, createdTime, updatedTime);
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = userRole;
        this.status = userStatus;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
