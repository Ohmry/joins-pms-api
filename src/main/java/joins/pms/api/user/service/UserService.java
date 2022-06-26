package joins.pms.api.user.service;

import joins.pms.api.user.domain.User;
import joins.pms.api.user.domain.UserInfo;
import joins.pms.api.user.exception.AlreadyEmailExistsException;
import joins.pms.api.user.exception.UserNotFoundException;
import joins.pms.api.user.model.PasswordUpdateRequest;
import joins.pms.api.user.model.SignupRequest;
import joins.pms.api.user.model.UserUpdateRequest;
import joins.pms.api.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfo createUser(SignupRequest signupRequest) {
        User user = User.create(signupRequest.email, signupRequest.password, signupRequest.name);
        user = userRepository.save(user);
        return UserInfo.valueOf(user);
    }

    public UserInfo getUserInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return UserInfo.valueOf(user);
    }

    public List<UserInfo> getAllUserInfo() {
        return userRepository.findAll().stream().map(UserInfo::valueOf).collect(Collectors.toList());
    }

    public UserInfo updateUser(Long id, UserUpdateRequest request) {
        User user = _findById(id);
        user.updateInfo(request.name);
        user = userRepository.save(user);
        return UserInfo.valueOf(user);
    }

    public void updateUserPassword(Long id, PasswordUpdateRequest request) {
        User user = _findById(id);
        user.verify(request.password);
        user.updatePassword(request.newPassword);
        userRepository.save(user);
    }

    public UserInfo signin(String email, String password) {
        User user = _findByEmail(email);
        user.verify(password);
        return UserInfo.valueOf(user);
    }

    public void checkEmailIsDuplicated(String email) {
        if (userRepository.existsByCredentialEmail(email)) {
            throw new AlreadyEmailExistsException(email);
        }
    }

    private User _findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
    private User _findByEmail(String email) {
        return userRepository.findByCredentialEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
}
