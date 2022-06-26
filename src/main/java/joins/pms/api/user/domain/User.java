package joins.pms.api.user.domain;

import joins.pms.core.domain.BaseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;

@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private UserCredential credential;
    @Embedded
    private UserToken token;
    @Column(length = 50)
    private String name;

    protected User() {
        super();
        this.credential = new UserCredential();
        this.token = new UserToken();
    }

    public static User create(String email, String password, String name){
        User user = new User();
        user.credential = UserCredential.create(email, password);
        user.token = new UserToken();
        user.name = name;
        return user;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.credential.getEmail();
    }

    public String getName() {
        return this.name;
    }

    public void verify(String password) {
        if (!this.credential.verify(password)) {
            throw new BadCredentialsException(this.credential.getEmail());
        }
    }

    public boolean verifyToken(String accessToken) {
        return this.token != null && this.token.verify(accessToken);
    }

    public boolean verifyToken(String accessToken, String refreshToken) {
        return this.token != null && this.token.verify(accessToken, refreshToken);
    }

    public void updateInfo(String name) {
        this.name = name;
    }

    public void updatePassword(String newPassword) {
        this.credential.updatePassword(newPassword);
    }

    public void updateToken(String accessToken, String refreshToken) {
        if (this.token == null) {
            this.token = new UserToken(accessToken, refreshToken);
        } else {
            this.token.updateToken(accessToken, refreshToken);
        }
    }

    public void resetToken() {
        this.token.reset();
    }
}
