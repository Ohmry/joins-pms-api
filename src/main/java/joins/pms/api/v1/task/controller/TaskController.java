package joins.pms.api.v1.task.controller;

import joins.pms.api.v1.task.model.TaskCreateRequest;
import joins.pms.api.v1.task.service.TaskService;
import joins.pms.core.http.ApiResponse;
import joins.pms.core.http.ApiStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/board/{boardId}/project/{projectId}/task/{id}")
    public ResponseEntity<ApiResponse> getTask(@PathVariable Long boardId, @PathVariable Long projectId, @PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse(ApiStatus.SUCCESS, new Object[] { boardId, projectId, id }));
    }

    @GetMapping("/board/{boardId}/project/{projectId}/task")
    public ResponseEntity<ApiResponse> getTaskList(@PathVariable Long boardId, @PathVariable Long projectId, @RequestParam int pageNo, @RequestParam int recordCount) {
        return ResponseEntity.ok(new ApiResponse(ApiStatus.SUCCESS, new Object[] { boardId, projectId, pageNo, recordCount }));
    }

    @PostMapping("/board/{boardId}/project/{projectId}/task")
    public ResponseEntity<ApiResponse> createTask(@PathVariable Long boardId, @PathVariable Long projectId, @RequestBody TaskCreateRequest request) {
        return ResponseEntity.ok(new ApiResponse(ApiStatus.SUCCESS, new Object[] { boardId, projectId, request }));
    }

    @PutMapping("/board/{boardId}/project/{projectId}/task")
    public ResponseEntity<ApiResponse> updateTask(@PathVariable Long boardId, @PathVariable Long projectId, @RequestBody TaskCreateRequest request) {
        return ResponseEntity.ok(new ApiResponse(ApiStatus.SUCCESS, new Object[] { boardId, projectId, request }));
    }

    @DeleteMapping("/board/{boardId}/project/{projectId}/task/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long boardId, @PathVariable Long projectId, @PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse(ApiStatus.SUCCESS, new Object[] { boardId, projectId, id }));
    }
}
