package joins.pms.api.v1.task.service;

import joins.pms.api.domain.Progress;
import joins.pms.api.domain.RowStatus;
import joins.pms.api.exception.DomainNotFoundException;
import joins.pms.api.v1.board.domain.Board;
import joins.pms.api.v1.board.repository.BoardRepository;
import joins.pms.api.v1.project.domain.Project;
import joins.pms.api.v1.project.repository.ProjectRepository;
import joins.pms.api.v1.task.domain.Task;
import joins.pms.api.v1.task.model.TaskInfo;
import joins.pms.api.v1.task.repository.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final BoardRepository boardRepository;
    
    public TaskService(TaskRepository taskRepository,
                       ProjectRepository projectRepository,
                       BoardRepository boardRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.boardRepository = boardRepository;
    }
    
    public Long createTask(Long boardId, Long projectId, String title, String description, String startDateTime, String endDateTime, Progress progress) {
        Board board = boardRepository.findByIdAndRowStatus(boardId, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Board.class));
        Project project = projectRepository.findByBoardIdAndBoardRowStatusAndIdAndRowStatus(boardId, RowStatus.NORMAL, projectId, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Project.class));
        
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
        
        Task task  = Task.create(board, project, title, description, startLocalDateTime, endLocalDateTime, progress);
        return taskRepository.save(task).getId();
    }
    
    public TaskInfo getTask(Long boardId, Long projectId, Long id) {
        Task task = taskRepository.findByBoardIdAndBoardRowStatusAndProjectIdAndProjectRowStatusAndIdAndRowStatus(boardId, RowStatus.NORMAL, projectId, RowStatus.NORMAL, id, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Task.class));
        return TaskInfo.valueOf(task);
    }
    
    public List<TaskInfo> getTaskList(Long boardId, Long projectId, int pageNo, int recordCount) {
        return taskRepository.findAllByBoardIdAndBoardRowStatusAndProjectIdAndProjectRowStatusAndRowStatus(boardId, RowStatus.NORMAL, projectId, RowStatus.NORMAL, RowStatus.NORMAL, PageRequest.of(pageNo, recordCount))
            .stream()
            .map(TaskInfo::valueOf)
            .collect(Collectors.toList());
    }
    
    public Long updateTask(Long boardId, Long projectId, Long id, String title, String description, String startDateTime, String endDateTime, Progress progress) {
        Task task = taskRepository.findByBoardIdAndBoardRowStatusAndProjectIdAndProjectRowStatusAndIdAndRowStatus(boardId, RowStatus.NORMAL, projectId, RowStatus.NORMAL, id, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Task.class));
        LocalDateTime startLocalDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        LocalDateTime endLocalDateTime = LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        task.update(Task.Field.title, title);
        task.update(Task.Field.description, description);
        task.update(Task.Field.startDateTime, startLocalDateTime);
        task.update(Task.Field.endDateTime, endLocalDateTime);
        task.update(Task.Field.progress, progress);
        return taskRepository.save(task).getId();
    }
    
    public void deleteTask(Long boardId, Long projectId, Long id) {
        Task task = taskRepository.findByBoardIdAndBoardRowStatusAndProjectIdAndProjectRowStatusAndIdAndRowStatus(boardId, RowStatus.NORMAL, projectId, RowStatus.NORMAL, id, RowStatus.NORMAL)
            .orElseThrow(() -> new DomainNotFoundException(Task.class));
        task.update(Task.Field.rowStatus, RowStatus.DELETED);
        taskRepository.save(task);
    }
}
