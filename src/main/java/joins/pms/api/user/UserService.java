package joins.pms.api.user;

import joins.pms.core.RowStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserInfo find (UUID id) {
        Optional<User> found = userRepository.findByIdAndRowStatus(id, RowStatus.NORMAL);
        return found.map(UserInfo::new).orElse(null);
    }

    public UserInfo findByEmail (String email) {
        Optional<User> found = userRepository.findByEmail(email);
        return found.map(UserInfo::new).orElse(null);
    }

    @Transactional
    public String update (UUID id, UserUpdateRequest userUpdateRequest) {
        Optional<User> found = userRepository.findById(id);
        UserDto userDto = found.map(UserDto::new).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        userDto.setName(userUpdateRequest.getName());
        userDto.setRole(userUpdateRequest.getRole());
        userDto.setStatus(userUpdateRequest.getStatus());
        User user = userRepository.save(userDto.toEntity());
        return user.getId().toString();
    }

    @Transactional
    public void delete (UUID id) {
        Optional<User> found = userRepository.findById(id);
        UserDto userDto = found.map(UserDto::new).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        userDto.setRowStatus(RowStatus.DELETED);
        userRepository.save(userDto.toEntity());
    }
}
