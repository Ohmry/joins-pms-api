package joins.pms.api.user.domain;

public class UserInfo {
    public final Long id;
    public final String email;
    public final String name;
    public final UserRole role;
    
    protected UserInfo(Long id, String email, String name, UserRole role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }
    
    public static UserInfo valueOf(User user) {
        return new UserInfo(user.getId(), user.getEmail(), user.getName(), user.getRole());
    }
}
