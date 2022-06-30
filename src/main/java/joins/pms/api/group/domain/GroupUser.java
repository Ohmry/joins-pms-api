package joins.pms.api.group.domain;

import joins.pms.api.domain.BaseEntity;
import joins.pms.api.user.domain.User;

import javax.persistence.*;

@Entity
@Table(name = "TB_CO_GROUP_USER")
public class GroupUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Group group;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    public GroupUser() {}
    public static GroupUser create (Group group, User user) {
        GroupUser groupUser = new GroupUser();
        groupUser.group = group;
        groupUser.user = user;
        return groupUser;
    }

    public Long getId() {
        return this.id;
    }

    public Group getGroup() {
        return this.group;
    }

    public User getUser() {
        return this.user;
    }
}
