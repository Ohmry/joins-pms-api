package joins.pms.api.user.domain;

import joins.pms.core.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserCredential implements Serializable {
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    @Column
    private String password;

    public String getEmail() {
        return email;
    }

    public static UserCredential create(String email, String password) {
        UserCredential credential = new UserCredential();
        PasswordEncoder encoder = Password.getInstance();
        credential.email = email;
        credential.password = encoder.encode(password);
        return credential;
    }

    public boolean verify(String password) {
        if (!StringUtils.hasText(password)) {
            return false;
        }

        PasswordEncoder encoder = Password.getInstance();
        return encoder.matches(password, this.password);
    }

    public void updatePassword(String newPassword) {
        if (!StringUtils.hasText(newPassword)) {
            throw new IllegalArgumentException("new password which is you want to change.");
        }

        PasswordEncoder encoder = Password.getInstance();
        this.password = encoder.encode(newPassword);
    }
}
