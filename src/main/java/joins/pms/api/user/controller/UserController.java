package joins.pms.api.user.controller;

import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import joins.pms.api.user.domain.UserInfo;
import joins.pms.api.user.model.*;
import joins.pms.api.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignupRequest request) {
        request.validate();
        userService.checkEmailIsExists(request.email);
        Long userId = userService.createUser(request.email, request.password, request.name);
        UserInfo userInfo = userService.getUser(userId);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header("Location", "/api/user/" + userId)
            .body(new ApiResponse(ApiStatus.SUCCESS, userInfo));
    }
    
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signin(@RequestBody SigninRequest request) {
        request.validate();
        userService.signin(request.email, request.password);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse(ApiStatus.SUCCESS));
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId) {
        UserInfo userInfo = userService.getUser(userId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse(ApiStatus.SUCCESS, userInfo));
    }
    
    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUserList(@RequestParam int pageNo, @RequestParam int recordCount) {
        List<UserInfo> userList = userService.getUserList(pageNo, recordCount);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse(ApiStatus.SUCCESS, userList));
    }
    
    @PutMapping("/user")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request) {
        request.validate();
        Long userId = userService.updateUser(request.id, request.name, request.role);
        UserInfo userInfo = userService.getUser(userId);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header("Location", "/api/user/" + userId)
            .body(new ApiResponse(ApiStatus.SUCCESS, userInfo));
    }
    
    @PutMapping("/user/credential")
    public ResponseEntity<ApiResponse> updateUserPassword(@RequestBody UserPasswordUpdateRequest request) {
        request.validate();
        Long userId = userService.updateUserPassword(request.email, request.password, request.newPassword);
        UserInfo userInfo = userService.getUser(userId);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header("Location", "/api/user/" + userId)
            .body(new ApiResponse(ApiStatus.USER_NEED_RESIGNIN, userInfo));
    }
    
    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(new ApiResponse(ApiStatus.SUCCESS));
    }
}
