package joins.pms.api.v1.project.controller;

import joins.pms.api.v1.project.domain.ProjectInfo;
import joins.pms.api.v1.project.model.ProjectCreateRequest;
import joins.pms.api.v1.project.model.ProjectUpdateRequest;
import joins.pms.api.v1.project.service.ProjectService;
import joins.pms.core.http.ApiResponse;
import joins.pms.core.http.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/board/{boardId}/project/{id}")
    public ResponseEntity<ApiResponse> getProject(@PathVariable Long boardId, @PathVariable Long id) {
        ProjectInfo projectInfo = projectService.getProject(boardId, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(ApiStatus.SUCCESS, projectInfo));
    }

    @GetMapping("/board/{boardId}/project")
    public ResponseEntity<ApiResponse> getProjectList(@PathVariable Long boardId, @RequestParam int pageNo, @RequestParam int recordCount) {
        List<ProjectInfo> projectList = projectService.getProjectList(boardId, pageNo, recordCount);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(ApiStatus.SUCCESS, projectList));
    }

    @PostMapping("/board/{boardId}/project")
    public ResponseEntity<ApiResponse> createProject(@PathVariable Long boardId, @RequestBody ProjectCreateRequest request) {
        request.validate();
        Long projectId = projectService.createProject(boardId, request);
        ProjectInfo projectInfo = projectService.getProject(boardId, projectId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/v1/board/" + boardId + "/project/" + projectId)
                .body(new ApiResponse(ApiStatus.SUCCESS, projectInfo));
    }

    @PutMapping("/board/{boardId}/project")
    public ResponseEntity<ApiResponse> updateProject(@PathVariable Long boardId, @RequestBody ProjectUpdateRequest request) {
        request.validate();
        Long projectId = projectService.updateProject(boardId, request);
        ProjectInfo projectInfo = projectService.getProject(boardId, projectId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/v1/board/" + boardId + "/project/" + projectId)
                .body(new ApiResponse(ApiStatus.SUCCESS, projectInfo));
    }

    @DeleteMapping("/board/{boardId}/project/{id}")
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable Long boardId, @PathVariable Long id) {
        projectService.deleteProject(boardId, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse(ApiStatus.SUCCESS));
    }
}
