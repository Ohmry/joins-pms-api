package joins.pms.api.user.application;

import joins.pms.api.user.domain.SignupRequest;
import joins.pms.api.user.domain.User;
import joins.pms.api.user.domain.UserInfo;
import joins.pms.api.user.infrastructure.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfo createUser (SignupRequest signupRequest) throws DataIntegrityViolationException {
//        if (User.existsByEmail(signupRequest.email)) {
//            throw new DataIntegrityViolationException(signupRequest.email);
//        }
        User user = User.create(signupRequest);
        user = userRepository.save(user);
        return user.isCreated() ? UserInfo.valueOf(user) : null;
    }

    public UserInfo getUserInfo (Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? UserInfo.valueOf(user) : null;
    }

    public List<UserInfo> getAllUserInfo () {
        return userRepository.findAll().stream().map(UserInfo::valueOf).collect(Collectors.toList());
    }

    public UserInfo signin (String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException());
        if (user.confirmPassword(password)) {
            return UserInfo.valueOf(user);
        } else {
            throw new BadCredentialsException(email);
        }
    }
}
