package joins.pms.api.user.model;

import joins.pms.api.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class ResigninRequest {
    public String refreshToken;

    public void validate() {
        if (!StringUtils.hasText(refreshToken)) {
            throw new IllegalRequestException();
        }
    }
}
