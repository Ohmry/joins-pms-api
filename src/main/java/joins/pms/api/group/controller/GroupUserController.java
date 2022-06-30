package joins.pms.api.group.controller;

import joins.pms.api.group.model.GroupInfo;
import joins.pms.api.group.model.GroupUserCreateRequest;
import joins.pms.api.group.service.GroupService;
import joins.pms.api.group.service.GroupUserService;
import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group/{groupId}/user")
public class GroupUserController {
    private final GroupService groupService;
    private final GroupUserService groupUserService;

    public GroupUserController(GroupService groupService,
                               GroupUserService groupUserService) {
        this.groupService = groupService;
        this.groupUserService = groupUserService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createGroupUser(@PathVariable Long groupId, @RequestBody GroupUserCreateRequest request) {
        request.validate();
        groupUserService.addUser(groupId, request.userId);
        GroupInfo groupInfo = groupService.getGroup(groupId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(ApiStatus.SUCCESS, groupInfo));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteGroupUser(@PathVariable Long groupId, @PathVariable Long userId) {
        groupUserService.deleteUser(groupId, userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse(ApiStatus.SUCCESS));
    }
}
