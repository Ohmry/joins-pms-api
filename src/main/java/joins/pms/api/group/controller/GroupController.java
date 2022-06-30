package joins.pms.api.group.controller;

import joins.pms.api.exception.UnauthorizationException;
import joins.pms.api.group.model.GroupInfo;
import joins.pms.api.group.model.GroupCreateRequest;
import joins.pms.api.group.model.GroupUpdateRequest;
import joins.pms.api.group.model.GroupUserAddRequest;
import joins.pms.api.group.service.GroupService;
import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import joins.pms.core.jwt.JwtToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createGroup(HttpServletRequest servletRequest, @RequestBody GroupCreateRequest request) {
        request.validate();
        groupService.checkNameExists(request.name);

        Object userIdObject = servletRequest.getAttribute(JwtToken.CLAIMS_USER_ID);
        if (userIdObject ==  null) {
            throw new UnauthorizationException();
        }

        Long groupId = groupService.createGroup(Long.parseLong(userIdObject.toString()), request.name);
        GroupInfo groupInfo = groupService.getGroup(groupId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/api/group/" + groupId)
                .body(new ApiResponse(ApiStatus.SUCCESS, groupInfo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getGroup(@PathVariable Long id) {
        GroupInfo groupInfo = groupService.getGroup(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(ApiStatus.SUCCESS, groupInfo));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getGroupList(@RequestParam int pageNo, @RequestParam int recordCount) {
        List<GroupInfo> groupList = groupService.getGroupList(pageNo, recordCount);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(ApiStatus.SUCCESS, groupList));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateGroup(@RequestBody GroupUpdateRequest request) {
        request.validate();
        groupService.checkNameExists(request.name);
        Long groupId = groupService.updateGroup(request.id, request.name);
        GroupInfo groupInfo = groupService.getGroup(groupId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/api/group/" + groupId)
                .body(new ApiResponse(ApiStatus.SUCCESS, groupInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse(ApiStatus.SUCCESS));
    }

    @PostMapping("/{id}/user")
    public ResponseEntity<ApiResponse> addGroupUser(@PathVariable Long id, @RequestBody GroupUserAddRequest request) {
        request.validate();
        Long groupId = groupService.addGroupUser(id, request.userId);
        GroupInfo groupInfo = groupService.getGroup(groupId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/api/group/" + groupId)
                .body(new ApiResponse(ApiStatus.SUCCESS, groupInfo));
    }

    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<ApiResponse> removeGroupUser(@PathVariable Long id, @PathVariable Long userId) {
        Long groupId = groupService.removeGroupUser(id, userId);
        GroupInfo groupInfo = groupService.getGroup(groupId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
