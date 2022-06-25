package joins.pms.api.user.domain;

public class UserInfo {
    private Long id;
    private String email;
    private String name;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public static UserInfo valueOf(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.id = user.getId();
        userInfo.email = user.getEmail();
        userInfo.name = user.getName();
        return userInfo;
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this.getClass() == obj.getClass()) return true;

        UserInfo userInfo = (UserInfo) obj;
        if (this.id.equals(userInfo.getId())) return true;
        if (this.email.equals(userInfo.getEmail())) return true;
        if (this.name.equals(userInfo.getName())) return true;
        return false;
    }
}
