package joins.pms.api.user.domain;

import joins.pms.api.domain.BaseEntity;
import joins.pms.api.domain.RowStatus;
import joins.pms.core.jwt.exception.JwtTokenInvalidException;

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
    @Embedded
    private UserToken token;
    
    public enum Field {
        name,
        role,
        password,
        accessToken,
        refreshToken,
        rowStatus
    }
    
    protected User() {}
    public static User create(String email, String password, String name, UserRole role) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.name = name;
        user.role = role;
        user.token = new UserToken();
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

    public UserToken getToken() {
        return this.token;
    }
    
    public void update(Field field, Object value) {
        if (field.equals(Field.accessToken) || field.equals(Field.refreshToken)) {
            if (this.token == null) {
                this.token = new UserToken();
            }
        }
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
            case accessToken:
                this.token.update(UserToken.Field.accessToken, value.toString());
                break;
            case refreshToken:
                this.token.update(UserToken.Field.refreshToken, value.toString());
                break;
            case rowStatus:
                this.rowStatus = RowStatus.valueOf(value.toString());
                break;
            default:
                break;
        }
    }

    public boolean validate(Field field, Object value) {
        switch (field) {
            case accessToken:
                return this.token.getAccessToken().equals(value);
            case refreshToken:
                return this.token.getRefreshToken().equals(value);
            default:
                return false;
        }
    }
}