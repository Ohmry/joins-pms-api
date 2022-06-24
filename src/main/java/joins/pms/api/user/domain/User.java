package joins.pms.api.user.domain;

import joins.pms.annotations.DomainLayer;
import joins.pms.domain.BaseEntity;
import joins.pms.core.PasswordFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@DomainLayer
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    @Column
    private String password;
    @Column(length = 50)
    private String name;

    @Transient
    private boolean isCreated;
    @Transient
    private boolean isUpdated;

    public static User create (SignupRequest signupRequest) {
        User user = new User();
        user.id = null;
        user.email = signupRequest.email;
        PasswordEncoder encoder = PasswordFactory.getInstance();
        user.password = encoder.encode(signupRequest.password);
        user.name = signupRequest.name;
        user.isCreated = false;
        return user;
    }

    public Long getId () {
        return this.id;
    }
    public String getEmail () {
        return this.email;
    }
    public String getName () {
        return this.name;
    }
    public boolean isCreated () {
        return this.isCreated;
    }
    public boolean isUpdated () {
        return this.isUpdated;
    }

    public boolean confirmPassword (String password) {
        PasswordEncoder encoder = PasswordFactory.getInstance();
        return encoder.matches(password, this.password);
    }

    @PrePersist
    public void prePersist () {
        super.prePersist();
        this.isCreated = false;
    }
    @PostPersist
    public void postPersist () {
        this.isCreated = true;
    }
    @PreUpdate
    public void preUpdate () {
        super.preUpdate();
        this.isUpdated = false;
    }
    @PostUpdate
    public void postUpdate () {
        this.isUpdated = true;
    }
}
