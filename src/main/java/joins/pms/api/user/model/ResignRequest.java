package joins.pms.api.user.model;

import joins.pms.api.v1.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class ResignRequest {
    public String refreshToken;

    public void validate() {
        if (!StringUtils.hasText(refreshToken)) {
            throw new IllegalRequestException();
        }
    }
}
