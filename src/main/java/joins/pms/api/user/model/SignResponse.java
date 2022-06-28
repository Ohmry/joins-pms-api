package joins.pms.api.user.model;

public class SignResponse {
    public final String accessToken;
    public final String refreshToken;

    public SignResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
