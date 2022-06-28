package joins.pms.api.user.model;

public class SigninResponse {
    public final String accessToken;
    public final String refreshToken;

    public SigninResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
