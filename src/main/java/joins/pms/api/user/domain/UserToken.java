package joins.pms.api.user.domain;

import joins.pms.core.jwt.exception.JwtTokenInvalidException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserToken {
    @Column
    private String accessToken;
    @Column
    private String refreshToken;

    public UserToken() {
        this.accessToken = null;
        this.refreshToken = null;
    }
    public UserToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public UserToken updateToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        return this;
    }

    public boolean verify(String accessToken) {
        return this.accessToken.equals(accessToken);
    }

    public boolean verify(String accessToken, String refreshToken) {
        return this.accessToken.equals(accessToken) && this.refreshToken.equals(refreshToken);
    }

    public void reset() {
        this.accessToken = null;
        this.refreshToken = null;
    }
}
