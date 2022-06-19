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

    public SigninDto signin (String email, String password) {
        Optional<User> credential = userRepository.findByEmail(email);
        if (!credential.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        User user = credential.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(email);
        }
        return new SigninDto(user);
    }

    @Transactional
    public UUID signup (SignupDto signupDto) {
        String encryptedPassword = passwordEncoder.encode(signupDto.getPassword());
        signupDto.setPassword(encryptedPassword);
        if (signupDto.getRole() == null) {
            signupDto.setRole(UserRole.USER);
        }
        if (signupDto.getStatus() == null) {
            signupDto.setStatus(UserStatus.ACTIVE);
        }
        signupDto.setRowStatus(RowStatus.NORMAL);
        User user = userRepository.save(signupDto.toEntity());
        return user.getId();
    }
}
