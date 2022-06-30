package joins.pms.api.group.service;

import joins.pms.api.domain.RowStatus;
import joins.pms.api.exception.DomainNotFoundException;
import joins.pms.api.group.domain.Group;
import joins.pms.api.group.model.GroupInfo;
import joins.pms.api.group.exception.GroupNameAlreadyExistsException;
import joins.pms.api.group.repository.GroupRepository;
import joins.pms.api.user.domain.User;
import joins.pms.api.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository,
                        UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Long createGroup(Long userId, String name) {
        User user = userRepository.findByIdAndRowStatus(userId, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(User.class));
        Group group = Group.create(name, user);
        return groupRepository.save(group).getId();
    }

    public GroupInfo getGroup(Long id) {
        Group group = groupRepository.findByIdAndRowStatus(id, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(Group.class));
        return GroupInfo.valueOf(group);
    }

    public List<GroupInfo> getGroupList(int pageNo, int recordCount) {
        return groupRepository
                .findAllByRowStatus(RowStatus.NORMAL, PageRequest.of(pageNo, recordCount))
                .stream()
                .map(GroupInfo::valueOf)
                .collect(Collectors.toList());
    }

    public Long updateGroup(Long id, String name) {
        Group group = groupRepository.findByIdAndRowStatus(id, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(Group.class));
        group.update(Group.Field.name, name);
        return groupRepository.save(group).getId();
    }

    public void deleteGroup(Long id) {
        Group group = groupRepository.findByIdAndRowStatus(id, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(Group.class));
        group.update(Group.Field.rowStatus, RowStatus.DELETED);
        groupRepository.save(group);
    }

    public void checkNameExists(String name) {
        if (groupRepository.existsByName(name)) {
            throw new GroupNameAlreadyExistsException();
        }
    }
}
