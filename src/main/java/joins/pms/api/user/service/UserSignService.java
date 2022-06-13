package joins.pms.api.user.service;

import joins.pms.api.user.exception.NotSuitableUserException;
import joins.pms.api.user.exception.UserNotFoundException;
import joins.pms.api.user.model.User;
import joins.pms.api.user.model.UserDto;
import joins.pms.api.user.repository.UserRepository;
import joins.pms.api.user.repository.UserSessionRepository;
import joins.pms.core.model.converter.ModelConverter;
import joins.pms.core.model.converter.exception.ConvertFailedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSignService {
    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;
    private final ModelConverter modelConverter;
    private final PasswordEncoder passwordEncoder;

    public UserSignService (UserRepository userRepository,
                            UserSessionRepository userSessionRepository,
                            ModelConverter modelConverter,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
        this.modelConverter = modelConverter;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto authorize (UserDto userDto) {
        if (userDto.getPassword() == null) {
            throw new NotSuitableUserException();
        }
        String rawPassword = userDto.getPassword();
        Optional<User> found = userRepository.findByEmail(userDto.getEmail());
        if (!found.isPresent()) {
            throw new UserNotFoundException();
        }
        userDto = found.map(user -> modelConverter.convert(user, UserDto.class)).orElseThrow(ConvertFailedException::new);
        if (!passwordEncoder.matches(rawPassword, userDto.getPassword())) {
            throw new NotSuitableUserException();
        }
        return userDto;
    }

    public UserDto deAuthorize (UserDto userDto) {
        return null;
    }
}
