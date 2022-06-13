package joins.pms.api.user.service;

import joins.pms.api.user.model.UserDto;
import joins.pms.core.model.converter.ModelConverter;
import joins.pms.api.user.model.UserSessionDto;
import joins.pms.api.user.model.UserSession;
import joins.pms.api.user.repository.UserSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserSessionService {
    private final UserSessionRepository userSessionRepository;
    private final ModelConverter modelConverter;
    public UserSessionService (UserSessionRepository userSessionRepository,
                               ModelConverter modelConverter) {
        this.userSessionRepository = userSessionRepository;
        this.modelConverter = modelConverter;
    }
    public LocalDateTime save (UserDto userDto, String jSessionId, int validMinute) {
        UserSessionDto userSessionDto = new UserSessionDto();
        userSessionDto.setId(userDto.getId());
        userSessionDto.setSession(jSessionId);
        userSessionDto.setExpireDateTime(LocalDateTime.now().plusMinutes(validMinute));
        UserSession userSession = modelConverter.convert(userSessionDto, UserSession.class);
        userSession = userSessionRepository.save(userSession);
        return userSession.getExpireDateTime();
    }

    public UserSessionDto findById (UUID id) {
        Optional<UserSession> found = userSessionRepository.findById(id);
        return found.map(userSession -> modelConverter.convert(userSession, UserSessionDto.class)).orElse(null);
    }
}
