package joins.pms.api.v1.project.domain;

import joins.pms.api.v1.board.domain.Board;
import joins.pms.core.domain.Progress;

import java.time.LocalDateTime;

public class ProjectInfo {
    public final Long id;
    public final String title;
    public final String description;
    public final LocalDateTime startDateTime;
    public final LocalDateTime endDateTime;
    public final Progress progress;
    public final Board board;

    protected ProjectInfo(Long id, String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, Progress progress, Board board) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.progress = progress;
        this.board = board;
    }

    public static ProjectInfo valueOf(Project project) {
        return new ProjectInfo(project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getStartDateTime(),
                project.getEndDateTime(),
                project.getProgress(),
                project.getBoard());
    }
}
