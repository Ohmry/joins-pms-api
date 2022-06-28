package joins.pms.api.v1.board.domain;

import joins.pms.api.v1.project.domain.Project;
import joins.pms.core.domain.BaseEntity;
import joins.pms.core.domain.RowStatus;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column
    private String title;
    @Column
    private String description;
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private Set<Project> projects;

    public Board() {
        super();
    }
    public Board(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }

    public enum Field {
        title,
        description,
        rowStatus
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
            default:
                break;
        }
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

}
