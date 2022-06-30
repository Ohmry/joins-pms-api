package joins.pms.api.v1.project.service;

import joins.pms.api.domain.Progress;
import joins.pms.api.domain.RowStatus;
import joins.pms.api.exception.DomainNotFoundException;
import joins.pms.api.v1.board.domain.Board;
import joins.pms.api.v1.board.repository.BoardRepository;
import joins.pms.api.v1.project.domain.Project;
import joins.pms.api.v1.project.domain.ProjectInfo;
import joins.pms.api.v1.project.repository.ProjectRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final BoardRepository bordBoardRepository;
    
    public ProjectService(ProjectRepository projectRepository,
                          BoardRepository boardRepository) {
        this.projectRepository = projectRepository;
        this.bordBoardRepository = boardRepository;
    }
    
    public Long createProject(Long boardId, String title, String description, String startDateTime, String endDateTime, Progress progress) {
        Board board = bordBoardRepository.findByIdAndRowStatus(boardId, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Board.class));
        
        LocalDateTime now = LocalDateTime.now();
        
        LocalDateTime startLocalDateTime;
        if (startDateTime != null) {
            startLocalDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        } else {
            startLocalDateTime = now;
        }
        LocalDateTime endLocalDateTime;
        if (endDateTime != null) {
            endLocalDateTime = LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        } else {
            endLocalDateTime = now;
        }
        
        Project project = Project.create(board, title, description, startLocalDateTime, endLocalDateTime, progress);
        return projectRepository.save(project).getId();
    }
    
    public ProjectInfo getProject(Long boardId, Long id) {
        Project project = projectRepository.findByBoardIdAndBoardRowStatusAndIdAndRowStatus(boardId, RowStatus.NORMAL, id, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Project.class));
        return ProjectInfo.valueOf(project);
    }
    
    public List<ProjectInfo> getProjectList(Long boardId, int pageNo, int recordCount) {
        return projectRepository
            .findAllByBoardIdAndBoardRowStatusAndRowStatus(boardId, RowStatus.NORMAL, RowStatus.NORMAL, PageRequest.of(pageNo, recordCount))
            .stream()
            .map(ProjectInfo::valueOf)
            .collect(Collectors.toList());
    }
    
    public Long updateProject(Long boardId, Long id, String title, String description, String startDateTime, String endDateTime, Progress progress) {
        Project project = projectRepository.findByBoardIdAndBoardRowStatusAndIdAndRowStatus(boardId, RowStatus.NORMAL, id, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Project.class));
        LocalDateTime startLocalDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        LocalDateTime endLocalDateTime = LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        project.update(Project.Field.title, title);
        project.update(Project.Field.description, description);
        project.update(Project.Field.startDateTime, startLocalDateTime);
        project.update(Project.Field.endDateTime, endLocalDateTime);
        project.update(Project.Field.progress, progress);
        return projectRepository.save(project).getId();
    }
    
    public void deleteProject(Long boardId, Long id) {
        Project project = projectRepository.findByBoardIdAndBoardRowStatusAndIdAndRowStatus(boardId, RowStatus.NORMAL, id, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Project.class));
        project.update(Project.Field.rowStatus, RowStatus.DELETED);
        projectRepository.save(project);
    }
}
