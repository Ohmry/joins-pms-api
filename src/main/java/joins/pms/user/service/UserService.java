package joins.pms.user.service;

import joins.pms.core.model.code.RowStatus;
import joins.pms.user.model.code.UserStatus;
import joins.pms.user.model.dto.UserDto;
import joins.pms.user.model.entity.User;
import joins.pms.user.repository.UserRepository;
import joins.pms.core.model.ModelConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
    public UserDto findById (UUID id) {
        Optional<User> found = userRepository.findById(id);
        return found.map(user -> modelConverter.convert(user, UserDto.class)).orElse(null);
    }
    public UUID save (UserDto userDto) {
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encryptedPassword);
        userDto.setUserStatus(UserStatus.ACTIVATED);
        User user = modelConverter.convert(userDto, User.class);
        user = userRepository.save(user);
        return user.getId();
    }
    public void delete (UUID id) {
        Optional<User> found = userRepository.findById(id);
        UserDto userDto = found.map(user -> modelConverter.convert(user, UserDto.class))
                .orElseThrow(() -> new NullPointerException(id.toString()));
        userDto.setStatus(RowStatus.DELETED);
    }
    public boolean signin (UserDto userDto) {
        String password = userDto.getPassword();
        Optional<User> found = userRepository.findByEmail(userDto.getEmail());
        userDto = found.map(user -> modelConverter.convert(user, UserDto.class)).orElse(null);
        return userDto != null && passwordEncoder.matches(password, userDto.getPassword());
    }
}
