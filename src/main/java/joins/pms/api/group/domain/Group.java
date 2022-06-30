package joins.pms.api.group.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import joins.pms.api.domain.BaseEntity;
import joins.pms.api.exception.DomainNotFoundException;
import joins.pms.api.user.domain.User;
import joins.pms.api.v1.board.domain.Board;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_CO_GROUP")
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false, unique = true)
    private String name;
    @OneToOne
    @JoinColumn(name = "owner")
    User owner;
    @JsonIgnore
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<GroupUser> users;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    List<Board> boards;

    public enum Field {
        name,
        users,
        boards,
        rowStatus
    }

    public Group() {
        this.users = new ArrayList<GroupUser>();
        this.boards = new ArrayList<Board>();
    }
    public static Group create(String name, User owner) {
        Group group = new Group();
        group.name = name;
        group.owner = owner;
        return group;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public User getOwner() {
        return this.owner;
    }

    public List<GroupUser> getUsers() {
        return this.users;
    }

    public List<Board> getBoards() {
        return this.boards;
    }

    public void update(Field field, Object value) {
        switch (field) {
            case name:
                this.name = value.toString();
                break;
            default:
                break;
        }
    }

    public void addUser(User user) {
        GroupUser groupUser = GroupUser.create(this, user);
        this.users.add(groupUser);
    }

    public void removeUser(User user) {
        GroupUser groupUser = this.users.stream()
                .filter(groupUserInfo -> groupUserInfo.getUser().equals(user))
                .findAny()
                .orElseThrow(() -> new DomainNotFoundException(GroupUser.class));
        this.users.remove(groupUser);
    }

    public void addBoard(Board board) {
        this.boards.add(board);
    }
}
