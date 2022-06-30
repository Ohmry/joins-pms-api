package joins.pms.api.v1.board.domain;

import joins.pms.api.domain.BaseEntity;
import joins.pms.api.domain.RowStatus;
import joins.pms.api.group.domain.Group;
import joins.pms.api.user.domain.User;
import joins.pms.api.v1.project.domain.Project;
import joins.pms.api.v1.task.domain.Task;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TB_PM_BOARD")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column
    private String description;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private User owner;
    @ManyToOne(targetEntity = Group.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    @OneToMany(mappedBy = "board")
    private Set<Project> projects;
    @OneToMany(mappedBy = "board")
    private Set<Task> tasks;
    
    protected Board() {}
    public static Board create(String title, String description) {
        Board board = new Board();
        board.title = title;
        board.description = description;
        return board;
    }
    
    public enum Field {
        title,
        description,
        rowStatus
    }
    
    public Long getId() {
        return this.id;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public Set<Task> getTasks() {
        return this.tasks;
    }

    public void update(Field field, Object value) {
        switch (field) {
            case title:
                this.title = value.toString();
                break;
            case description:
                this.description = value.toString();
                break;
            case rowStatus:
                this.rowStatus = RowStatus.valueOf(value.toString());
                break;
            default:
                break;
        }
    }
}
