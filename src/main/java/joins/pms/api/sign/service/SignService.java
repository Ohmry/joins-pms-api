package joins.pms.api.sign.service;

import joins.pms.api.user.model.User;
import joins.pms.api.user.model.UserDto;
import joins.pms.api.user.model.UserRole;
import joins.pms.api.user.model.UserStatus;
import joins.pms.api.user.repository.UserRepository;
import joins.pms.core.model.RowStatus;
import joins.pms.core.model.converter.ModelConverter;
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
    private final ModelConverter modelConverter;

    public SignService (UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        ModelConverter modelConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelConverter = modelConverter;
    }

    public UserDto signin (String email, String password) {
        Optional<User> credential = userRepository.findByEmail(email);
        if (!credential.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        User user = credential.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(email);
        }
        return modelConverter.convert(user, UserDto.class);
    }

    @Transactional
    public UUID signup (UserDto userDto) {
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);
        if (userDto.getUserRole() == null) {
            userDto.setUserRole(UserRole.USER);
        }
        if (userDto.getUserStatus() == null) {
            userDto.setUserStatus(UserStatus.ACTIVATED);
        }
        if (userDto.getRowStatus() == null) {
            userDto.setRowStatus(RowStatus.NORMAL);
        }
        User user = modelConverter.convert(userDto, User.class);
        user = userRepository.save(user);
        return user.getId();
    }
}
