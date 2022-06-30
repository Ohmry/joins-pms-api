package joins.pms.api.user.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserToken {
    @Column
    private String accessToken;
    @Column
    private String refreshToken;

    public enum Field {
        accessToken,
        refreshToken
    }

    public UserToken() {}
    public UserToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void update(Field field, String value) {
        switch (field) {
            case accessToken:
                this.accessToken = value;
                break;
            case refreshToken:
                this.refreshToken = value;
                break;
            default:
                break;
        }
    }

    public void reset() {
        this.accessToken = "";
        this.refreshToken = "";
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }
}
