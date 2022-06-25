package joins.pms.api.user.domain;

import joins.pms.core.domain.BaseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import javax.persistence.*;

@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private UserCredential credential;
    @Column(length = 50)
    private String name;

    protected User() {
        super();
    }

    public static User create(String email, String password, String name){
        User user = new User();
        user.credential = UserCredential.create(email, password);
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

    public void updateInfo(String name) {
        this.name = name;
    }

    public void updatePassword(String newPassword) {
        this.credential.updatePassword(newPassword);
    }
}
