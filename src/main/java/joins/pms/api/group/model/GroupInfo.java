package joins.pms.api.group.model;

import joins.pms.api.group.domain.Group;
import joins.pms.api.user.model.UserSimpleInfo;
import joins.pms.api.v1.board.domain.Board;

import java.util.List;
import java.util.stream.Collectors;

public class GroupInfo {
    public final Long id;
    public final String name;
    public final Long ownerId;
    public final String ownerName;
    public final List<UserSimpleInfo> users;
    public final List<Board> boards;

    protected GroupInfo(Long id, String name, Long ownerId, String ownerName, List<UserSimpleInfo> users, List<Board> boards) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.users = users;
        this.boards = boards;
    }

    public static GroupInfo valueOf(Group group) {
        List<UserSimpleInfo> userList = group.getUsers().stream()
                .map(groupUser -> UserSimpleInfo.valueOf(groupUser.getUser()))
                .collect(Collectors.toList());
        return new GroupInfo(group.getId(),
                group.getName(),
                group.getOwner().getId(),
                group.getOwner().getName(),
                userList,
                group.getBoards());
    }
}
