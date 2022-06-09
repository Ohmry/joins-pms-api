package joins.pms.api.user.service;

import joins.pms.core.model.ModelConverter;
import joins.pms.api.user.model.UserSessionDto;
import joins.pms.api.user.model.UserSession;
import joins.pms.api.user.repository.UserSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserSessionService {
    private final UserSessionRepository userSessionRepository;
    private final ModelConverter modelConverter;
    public UserSessionService (UserSessionRepository userSessionRepository,
                               ModelConverter modelConverter) {
        this.userSessionRepository = userSessionRepository;
        this.modelConverter = modelConverter;
    }
    public LocalDateTime save (UserSessionDto userSessionDto) {
        UserSession userSession = modelConverter.convert(userSessionDto, UserSession.class);
        userSession = userSessionRepository.save(userSession);
        return userSession.getExpireDateTime();
    }
}
