package joins.pms.api.user.domain;

import joins.pms.api.domain.BaseEntity;
import joins.pms.api.domain.RowStatus;

import javax.persistence.*;

@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 50, nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;
    
    public enum Field {
        name,
        role,
        password,
        rowStatus
    }
    
    protected User() {}
    public static User create(String email, String password, String name, UserRole role) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.name = name;
        user.role = role;
        return user;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getName() {
        return this.name;
    }
    
    public UserRole getRole() {
        return this.role;
    }
    
    public void update(Field field, Object value) {
        switch (field) {
            case name:
                this.name = value.toString();
                break;
            case role:
                this.role = UserRole.valueOf(value.toString());
                break;
            case password:
                this.password = value.toString();
                break;
            case rowStatus:
                this.rowStatus = RowStatus.valueOf(value.toString());
                break;
            default:
                break;
                
        }
    }
}
