package joins.pms.api.user.model;

import joins.pms.api.user.domain.UserToken;
import joins.pms.core.annotations.ValueObject;

@ValueObject
public class UserTokenInfo {
    public final long id;
    public final String accessToken;
    public final String refreshToken;

    public UserTokenInfo(long userId, String accessToken, String refreshToken) {
        this.id = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static UserTokenInfo valueOf(long userId, UserToken userToken) {
        return new UserTokenInfo(userId, userToken.getAccessToken(),userToken.getRefreshToken());
    }
}
