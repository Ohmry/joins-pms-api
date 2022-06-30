package joins.pms.api.v1.board.model;

import joins.pms.api.v1.board.domain.Board;
import joins.pms.api.v1.project.model.ProjectSimpleInfo;
import joins.pms.core.annotations.ValueObject;

import java.util.Set;
import java.util.stream.Collectors;

@ValueObject
public class BoardInfo {
    public final Long id;
    public final String title;
    public final String description;
    public final Set<ProjectSimpleInfo> projects;
    
    public BoardInfo(Long id, String title, String description, Set<ProjectSimpleInfo> projects) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.projects = projects;
    }
    
    public static BoardInfo valueOf(Board board) {
        Set<ProjectSimpleInfo> projectList = board.getProjects().stream()
                .map(ProjectSimpleInfo::valueOf)
                .collect(Collectors.toSet());
        return new BoardInfo(board.getId(), board.getTitle(), board.getDescription(), projectList);
    }
}
