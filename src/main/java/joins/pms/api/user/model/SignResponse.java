package joins.pms.api.user.model;

import joins.pms.core.domain.BaseDataTransferObject;

public class SignResponse extends BaseDataTransferObject {
    public final String accessToken;
    public final String refreshToken;

    public SignResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
