package joins.pms.api.group.model;

import joins.pms.api.group.domain.Group;

public class GroupSimpleInfo {
    public final Long id;
    public final String name;

    protected GroupSimpleInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GroupSimpleInfo valueOf(Group group) {
        return new GroupSimpleInfo(group.getId(), group.getName());
    }
}
