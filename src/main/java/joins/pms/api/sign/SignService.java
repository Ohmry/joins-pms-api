package joins.pms.api.sign;

import joins.pms.api.user.User;
import joins.pms.api.user.UserRepository;
import joins.pms.api.user.UserRole;
import joins.pms.api.user.UserStatus;
import joins.pms.core.RowStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class SignService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignService (UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SigninResponse signin (String email, String password) {
        Optional<User> credential = userRepository.findByEmail(email);
        if (!credential.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        User user = credential.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(email);
        }
        return new SigninResponse(user);
    }

    @Transactional
    public UUID signup (SignupRequest signupRequest) {
        String encryptedPassword = passwordEncoder.encode(signupRequest.getPassword());
        signupRequest.setPassword(encryptedPassword);
        if (signupRequest.getRole() == null) {
            signupRequest.setRole(UserRole.USER);
        }
        if (signupRequest.getStatus() == null) {
            signupRequest.setStatus(UserStatus.ACTIVE);
        }
        signupRequest.setRowStatus(RowStatus.NORMAL);
        User user = userRepository.save(signupRequest.toEntity());
        return user.getId();
    }
}
