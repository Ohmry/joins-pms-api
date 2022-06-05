package joins.pms.api.v1.service;

import joins.pms.api.v1.model.dto.UserDto;
import joins.pms.api.v1.model.entity.User;
import joins.pms.api.v1.repository.UserRepository;
import joins.pms.core.model.ModelConverter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelConverter modelConverter;

    public UserService(UserRepository userRepository, ModelConverter modelConverter) {
        this.userRepository = userRepository;
        this.modelConverter = modelConverter;
    }

    public UserDto findById (Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> modelConverter.convert(value, UserDto.class)).orElse(null);
    }

    public Long save (UserDto userDto) {
        User user = modelConverter.convert(userDto, User.class);
        user = userRepository.save(user);
        return user.getId();
    }

    public void delete (Long id) {
        userRepository.deleteById(id);
    }
}
