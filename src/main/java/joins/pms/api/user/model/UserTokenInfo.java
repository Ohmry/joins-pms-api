package joins.pms.api.user.model;

import joins.pms.api.user.domain.UserToken;

public class UserTokenInfo {
    public final String accessToken;
    public final String refreshToken;

    public UserTokenInfo(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static UserTokenInfo valueOf(UserToken userToken) {
        return new UserTokenInfo(userToken.getAccessToken(),userToken.getRefreshToken());
    }
}
