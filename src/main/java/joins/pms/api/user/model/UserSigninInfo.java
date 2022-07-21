package joins.pms.api.user.model;

public class UserSigninInfo extends UserSimpleInfo {
    public final UserTokenInfo token;

    protected UserSigninInfo(Long id, String email, String name, UserTokenInfo userTokenInfo) {
        super(id, email, name);
        this.token = userTokenInfo;
    }

    public static UserSigninInfo valueOf(UserInfo userInfo, UserTokenInfo userTokenInfo) {
        return new UserSigninInfo(userInfo.id, userInfo.email, userInfo.name, userTokenInfo);
    }
}
