package joins.pms.api.user.service;

import joins.pms.api.domain.RowStatus;
import joins.pms.api.exception.BadCredentialException;
import joins.pms.api.exception.DomainNotFoundException;
import joins.pms.api.user.domain.User;
import joins.pms.api.user.domain.UserInfo;
import joins.pms.api.user.domain.UserRole;
import joins.pms.api.user.exception.UserEmailAlreadyExistsException;
import joins.pms.api.user.repository.UserRepository;
import joins.pms.core.utils.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void signin(String email, String password) {
        User user = userRepository.findByEmailAndRowStatus(email, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(User.class));
        PasswordEncoder encoder = Password.getInstance();
        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialException();
        }
    }
    
    public Long createUser(String email, String password, String name) {
        PasswordEncoder encoder = Password.getInstance();
        User user = User.create(email, encoder.encode(password), name, UserRole.USER);
        return userRepository.save(user).getId();
    }
    
    public UserInfo getUser(Long userId) {
        User user = userRepository.findByIdAndRowStatus(userId, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(User.class));
        return UserInfo.valueOf(user);
    }
    
    public List<UserInfo> getUserList(int pageNo, int recordCount) {
        return userRepository.findAllByRowStatus(RowStatus.NORMAL)
            .stream()
            .map(UserInfo::valueOf)
            .collect(Collectors.toList());
    }
    
    public Long updateUser(Long userId, String name, UserRole role) {
        User user = userRepository.findByIdAndRowStatus(userId, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(User.class));
        user.update(User.Field.name, name);
        user.update(User.Field.role, role);
        return userRepository.save(user).getId();
    }
    
    public Long updateUserPassword(String email, String password, String newPassword) {
        User user = userRepository.findByEmailAndRowStatus(email, RowStatus.NORMAL)
            .orElseThrow(BadCredentialException::new);
        PasswordEncoder encoder = Password.getInstance();
        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialException();
        }
        user.update(User.Field.password, encoder.encode(newPassword));
        return userRepository.save(user).getId();
    }
    
    public void deleteUser(Long userId) {
        User user = userRepository.findByIdAndRowStatus(userId, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(User.class));
        user.update(User.Field.rowStatus, RowStatus.DELETED);
        userRepository.save(user);
    }
    
    public void checkEmailIsExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserEmailAlreadyExistsException(email);
        }
    }
}
