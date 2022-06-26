package joins.pms.api.user.service;

import joins.pms.api.user.domain.User;
import joins.pms.api.user.domain.UserInfo;
import joins.pms.api.user.exception.AlreadyEmailExistsException;
import joins.pms.api.user.exception.UserNotFoundException;
import joins.pms.api.user.model.PasswordUpdateRequest;
import joins.pms.api.user.model.SignResponse;
import joins.pms.api.user.model.SignupRequest;
import joins.pms.api.user.model.UserUpdateRequest;
import joins.pms.api.user.repository.UserRepository;
import joins.pms.core.jwt.JwtAuthentication;
import joins.pms.core.jwt.JwtTokenProvider;
import joins.pms.core.jwt.exception.JwtTokenInvalidException;
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

    public void resetToken(Long id) {
        User user = _findById(id);
        user.resetToken();
        userRepository.save(user);
    }

    public SignResponse signin(String email, String password) {
        User user = _findByEmail(email);
        user.verify(password);
        String accessToken = JwtTokenProvider.generateAccessToken(email);
        String refreshToken = JwtTokenProvider.generateRefreshToken(email);
        user.updateToken(accessToken, refreshToken);
        userRepository.save(user);
        return new SignResponse(accessToken, refreshToken);
    }

    public void updateToken(UserInfo userInfo, String accessToken, String refreshToken) {
        User user = userRepository.findByCredentialEmail(userInfo.getEmail()).orElseThrow(UserNotFoundException::new);
        user.updateToken(accessToken, refreshToken);
        userRepository.save(user);
    }

    public void checkEmailIsDuplicated(String email) {
        if (userRepository.existsByCredentialEmail(email)) {
            throw new AlreadyEmailExistsException(email);
        }
    }

    public SignResponse resign(String accessToken, String refreshToken) {
        String email = JwtTokenProvider.getPrincipal(accessToken);
        User user = userRepository.findByCredentialEmail(email).orElseThrow(UserNotFoundException::new);
        user.verifyToken(accessToken, refreshToken);

        String newAccessToken = JwtTokenProvider.generateAccessToken(email);
        String newRefreshToken = JwtTokenProvider.generateRefreshToken(email);
        user.updateToken(newAccessToken, newRefreshToken);
        userRepository.save(user);
        return new SignResponse(newAccessToken, newRefreshToken);
    }

    public void verifyToken(String email, String accessToken) {
        User user = userRepository.findByCredentialEmail(email).orElseThrow(UserNotFoundException::new);
        if (!user.verifyToken(accessToken)) {
            throw new JwtTokenInvalidException();
        }
    }

    private User _findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
    private User _findByEmail(String email) {
        return userRepository.findByCredentialEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
