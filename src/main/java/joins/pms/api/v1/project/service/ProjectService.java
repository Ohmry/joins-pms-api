package joins.pms.api.v1.project.service;

import joins.pms.api.v1.board.domain.Board;
import joins.pms.api.v1.board.domain.BoardInfo;
import joins.pms.api.v1.board.repository.BoardRepository;
import joins.pms.api.v1.exception.DomainNotFoundException;
import joins.pms.api.v1.project.domain.Project;
import joins.pms.api.v1.project.domain.ProjectInfo;
import joins.pms.api.v1.project.model.ProjectCreateRequest;
import joins.pms.api.v1.project.model.ProjectUpdateRequest;
import joins.pms.api.v1.project.repository.ProjectRepository;
import joins.pms.core.domain.Progress;
import joins.pms.core.domain.RowStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final BoardRepository boardRepository;

    public ProjectService(ProjectRepository projectRepository,
                          BoardRepository boardRepository) {
        this.projectRepository = projectRepository;
        this.boardRepository = boardRepository;
    }

    public ProjectInfo getProject(Long boardId, Long projectId) {
        Project project = projectRepository.findByBoardIdAndIdAndRowStatus(boardId, projectId, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(Project.class));
        return ProjectInfo.valueOf(project);
    }

    public List<ProjectInfo> getProjectList(Long boardId, int pageNo, int recordCount) {
        return projectRepository.findAllByBoardIdAndRowStatus(boardId, RowStatus.NORMAL, PageRequest.of(pageNo, recordCount))
                .stream()
                .map(ProjectInfo::valueOf)
                .collect(Collectors.toList());
    }

    public Long createProject(Long boardId, ProjectCreateRequest request) {
        Board board = boardRepository.findByIdAndRowStatus(boardId, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(Board.class));
        Project project = new Project(board,
                request.title,
                request.descriptoin,
                request.startDateTime,
                request.endDateTime,
                request.progress);
        return projectRepository.save(project).getId();
    }

    public Long updateProject(Long boardId, ProjectUpdateRequest request) {
        Project project = projectRepository.findByBoardIdAndIdAndRowStatus(boardId, request.id, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(Project.class));
        project.update(Project.Field.title, request.title);
        project.update(Project.Field.description, request.descriptoin);
        project.update(Project.Field.startDateTime, request.startDateTime);
        project.update(Project.Field.endDateTime, request.endDateTime);
        project.update(Project.Field.progress, request.progress);
        return projectRepository.save(project).getId();
    }

    public void deleteProject(Long boardId, Long projectId) {
        Project project = projectRepository.findByBoardIdAndIdAndRowStatus(boardId, projectId, RowStatus.NORMAL)
                .orElseThrow(() -> new DomainNotFoundException(Project.class));
        project.update(Project.Field.rowStatus, RowStatus.DELETED);
        projectRepository.save(project);
    }
}
