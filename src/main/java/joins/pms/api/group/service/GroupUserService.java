package joins.pms.api.group.service;

import joins.pms.api.domain.RowStatus;
import joins.pms.api.exception.DomainNotFoundException;
import joins.pms.api.group.domain.Group;
import joins.pms.api.group.domain.GroupUser;
import joins.pms.api.group.repository.GroupRepository;
import joins.pms.api.group.repository.GroupUserRepository;
import joins.pms.api.user.domain.User;
import joins.pms.api.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupUserService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupUserRepository groupUserRepository;

    public GroupUserService(GroupRepository groupRepository,
                            UserRepository userRepository,
                            GroupUserRepository groupUserRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupUserRepository = groupUserRepository;
    }

    public Long addUser(Long groupId, Long userId) {
        Group group = groupRepository.findByIdAndRowStatus(groupId, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(Group.class));
        User user = userRepository.findByIdAndRowStatus(userId, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(User.class));
        GroupUser groupUser = GroupUser.create(group, user);
        groupUserRepository.save(groupUser);
        return groupId;
    }

    public void deleteUser(Long groupId, Long userId) {
        GroupUser groupUser = groupUserRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new DomainNotFoundException(GroupUser.class));
        groupUserRepository.delete(groupUser);
    }
}
