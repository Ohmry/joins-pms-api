package joins.pms.core;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordFactory {
    private static PasswordEncoder passwordEncoder;

    public PasswordFactory () {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public static PasswordEncoder getInstance () {
        return passwordEncoder;
    }
}
