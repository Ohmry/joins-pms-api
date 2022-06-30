package joins.pms.api.user.service;

import io.jsonwebtoken.Claims;
import joins.pms.api.domain.RowStatus;
import joins.pms.api.exception.BadCredentialException;
import joins.pms.api.exception.DomainNotFoundException;
import joins.pms.api.exception.IllegalRequestException;
import joins.pms.api.exception.UnauthorizationException;
import joins.pms.api.user.domain.*;
import joins.pms.api.user.exception.UserEmailAlreadyExistsException;
import joins.pms.api.user.model.UserInfo;
import joins.pms.api.user.model.UserTokenInfo;
import joins.pms.api.user.repository.UserRepository;
import joins.pms.core.jwt.JwtToken;
import joins.pms.core.jwt.exception.JwtTokenInvalidException;
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
    
    public UserInfo signin(String email, String password) {
        User user = userRepository.findByEmailAndRowStatus(email, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(User.class));
        PasswordEncoder encoder = Password.getInstance();
        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialException();
        }
        return UserInfo.valueOf(user);
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
    
    public Long updateUserPassword(Long userId, String password, String newPassword) {
        User user = userRepository.findByIdAndRowStatus(userId, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(User.class));
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
    
    public String getUserRole(String email) {
        User user = userRepository.findByEmailAndRowStatus(email, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(User.class));
        return user.getRole().name();
    }
    
    public void validateSession(Object sessionUserIdObject, Long requestUserId) {
        if (sessionUserIdObject == null) {
            throw new UnauthorizationException();
        }
        
        Long sessionUserId = Long.parseLong(sessionUserIdObject.toString());
        if (!sessionUserId.equals(requestUserId)) {
            throw new IllegalRequestException();
        }
    }

    public UserTokenInfo createToken(Long userId, String email, String clientIp) {
        User user = userRepository.findByIdAndRowStatus(userId, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(User.class));
        String userRole = user.getRole().name();

        String accessToken = JwtToken.generate(JwtToken.Type.accessToken, userId, email, clientIp, userRole);
        String refreshToken = JwtToken.generate(JwtToken.Type.refreshToken, userId, email, clientIp, userRole);

        user.update(User.Field.accessToken, accessToken);
        user.update(User.Field.refreshToken, refreshToken);
        userRepository.save(user);

        return UserTokenInfo.valueOf(user.getToken());
    }

    public UserTokenInfo refreshToken(Long userId, String accessToken, String refreshToken) {
        User user = userRepository.findByIdAndRowStatus(userId, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(User.class));
        if (!user.validate(User.Field.accessToken, accessToken)) {
            throw new JwtTokenInvalidException();
        }
        if (!user.validate(User.Field.refreshToken, refreshToken)) {
            throw new JwtTokenInvalidException();
        }

        Claims claims = JwtToken.getClaims(accessToken);
        accessToken = JwtToken.generate(JwtToken.Type.accessToken, claims);
        refreshToken = JwtToken.generate(JwtToken.Type.refreshToken, claims);

        user.update(User.Field.accessToken, accessToken);
        user.update(User.Field.refreshToken, refreshToken);
        userRepository.save(user);

        return UserTokenInfo.valueOf(user.getToken());
    }

    public void validateToken(Long userId, String accessToken) {
        User user = userRepository.findByIdAndRowStatus(userId, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(User.class));
        if (!user.validate(User.Field.accessToken, accessToken)) {
            throw new JwtTokenInvalidException();
        }

    }
}
