package joins.pms.api.user.model;

import joins.pms.api.group.model.GroupSimpleInfo;
import joins.pms.api.user.domain.User;
import joins.pms.api.user.domain.UserRole;
import joins.pms.core.annotations.ValueObject;

import java.util.List;
import java.util.stream.Collectors;

@ValueObject
public class UserInfo {
    public final Long id;
    public final String email;
    public final String name;
    public final UserRole role;
    public final List<GroupSimpleInfo> groups;
    
    protected UserInfo(Long id, String email, String name, UserRole role, List<GroupSimpleInfo> groups) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.groups = groups;
    }
    
    public static UserInfo valueOf(User user) {
        List<GroupSimpleInfo> groupList = user.getGroups().stream()
                .map(groupUser -> GroupSimpleInfo.valueOf(groupUser.getGroup()))
                .collect(Collectors.toList());
        return new UserInfo(user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                groupList);
    }
}
