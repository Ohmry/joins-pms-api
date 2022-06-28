package joins.pms.api.v1.task.controller;

import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import joins.pms.api.v1.task.domain.TaskInfo;
import joins.pms.api.v1.task.model.TaskCreateRequest;
import joins.pms.api.v1.task.model.TaskUpdateRequest;
import joins.pms.api.v1.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TaskController {
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @PostMapping("/board/{boardId}/project/{projectId}/task")
    public ResponseEntity<ApiResponse> createTask(@PathVariable Long boardId, @PathVariable Long projectId, @RequestBody TaskCreateRequest request) {
        request.validate();
        Long taskId = taskService.createTask(boardId, projectId, request.title, request.description,
            request.startDateTime, request.endDateTime, request.progress);
        TaskInfo taskInfo = taskService.getTask(boardId, projectId, taskId);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header("Location", "/api/v1/board/" + boardId + "/project/" + projectId + "/task/" + taskId)
            .body(new ApiResponse(ApiStatus.SUCCESS, taskInfo));
    }
    
    @GetMapping("/board/{boardId}/project/{projectId}/task/{taskId}")
    public ResponseEntity<ApiResponse> getTask(@PathVariable Long boardId, @PathVariable Long projectId, @PathVariable Long taskId) {
        TaskInfo taskInfo = taskService.getTask(boardId, projectId, taskId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse(ApiStatus.SUCCESS, taskInfo));
    }
    
    @GetMapping("/board/{boardId}/project/{projectId}/task")
    public ResponseEntity<ApiResponse> getTaskList(@PathVariable Long boardId, @PathVariable Long projectId, @RequestParam int pageNo, @RequestParam int recordCount) {
        List<TaskInfo> taskList = taskService.getTaskList(boardId, projectId, pageNo, recordCount);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse(ApiStatus.SUCCESS, taskList));
    }
    
    @PutMapping("/board/{boardId}/project/{projectId}/task")
    public ResponseEntity<ApiResponse> updateTask(@PathVariable Long boardId, @PathVariable Long projectId, @RequestBody TaskUpdateRequest request) {
        request.validate();
        Long taskId = taskService.updateTask(boardId, projectId, request.id, request.title, request.description,
            request.startDateTime, request.endDateTime, request.progress);
        TaskInfo taskInfo = taskService.getTask(boardId, projectId, taskId);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header("Location", "/api/v1/board/" + boardId + "/project/" + projectId + "/task/" + taskId)
            .body(new ApiResponse(ApiStatus.SUCCESS, taskInfo));
    }
    
    @DeleteMapping("/board/{boardId}/project/{projectId}/task/{taskId}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long boardId, @PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.deleteTask(boardId, projectId, taskId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(new ApiResponse(ApiStatus.SUCCESS));
    }
}
