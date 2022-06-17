package joins.pms.api.user.service;

import joins.pms.core.model.RowStatus;
import joins.pms.api.user.model.UserRole;
import joins.pms.api.user.model.UserStatus;
import joins.pms.api.user.model.UserDto;
import joins.pms.api.user.model.User;
import joins.pms.api.user.repository.UserRepository;
import joins.pms.core.model.converter.ModelConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * 사용자(User) 관련 서비스
 *
 * @see UserRepository
 * @version 1.0
 * @author Ohmry
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelConverter modelConverter;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       ModelConverter modelConverter,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelConverter = modelConverter;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto find (UUID id) {
        Optional<User> found = userRepository.findByIdAndRowStatus(id, RowStatus.NORMAL);
        return found.map(user -> modelConverter.convert(user, UserDto.class)).orElse(null);
    }

    public UserDto findByEmail (String email) {
        Optional<User> found = userRepository.findByEmail(email);
        return found.map(user -> modelConverter.convert(user, UserDto.class)).orElse(null);
    }

    @Transactional
    public UUID save (UserDto userDto) {
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

    @Transactional
    public void delete (UUID id) {
        Optional<User> found = userRepository.findById(id);
        UserDto userDto = found.map(user -> modelConverter.convert(user, UserDto.class))
                .orElseThrow(() -> new NullPointerException(id.toString()));
        userDto.setRowStatus(RowStatus.DELETED);
        User user = modelConverter.convert(userDto, User.class);
        userRepository.save(user);
    }

    public UserDto signin (UserDto userDto) {
        String password = userDto.getPassword();
        if (password == null) return null;
        Optional<User> found = userRepository.findByEmail(userDto.getEmail());
        userDto = found.map(user -> modelConverter.convert(user, UserDto.class)).orElse(null);
        if (userDto != null && passwordEncoder.matches(password, userDto.getPassword())) {
            return userDto;
        } else {
            return null;
        }
    }

    public String encryptPassword (String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }
}
