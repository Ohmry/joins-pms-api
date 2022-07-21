package joins.pms.api.user.controller;

import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import joins.pms.api.user.model.*;
import joins.pms.api.user.service.UserService;
import joins.pms.core.annotations.AdminOnly;
import joins.pms.core.jwt.JwtToken;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<ApiResponse> signin(HttpServletRequest servletRequest, @RequestBody SigninRequest request) {
        request.validate();
        UserInfo userInfo = userService.signin(request.email, request.password);
        String clientIp = servletRequest.getRemoteAddr();
        UserTokenInfo userTokenInfo = userService.createToken(userInfo.id, userInfo.email, clientIp);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse(ApiStatus.SUCCESS, UserSigninInfo.valueOf(userInfo, userTokenInfo)));
    }

    @PostMapping("/resignin")
    public ResponseEntity<ApiResponse> resignin(HttpServletRequest servletRequest, @RequestBody SigninRequest request) {
        String authorization =  servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        JwtToken jwtToken = JwtToken.valueOf(authorization);

        request.validate();
        UserInfo userInfo = userService.signin(request.email, request.password);
        String clientIp = servletRequest.getRemoteAddr();
        UserTokenInfo userTokenInfo = userService.createToken(userInfo.id, userInfo.email, clientIp);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(ApiStatus.SUCCESS, userTokenInfo));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long id) {
        UserInfo userInfo = userService.getUser(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse(ApiStatus.SUCCESS, userInfo));
    }
    
    @AdminOnly
    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUserList(@RequestParam int pageNo, @RequestParam int recordCount) {
        List<UserInfo> userList = userService.getUserList(pageNo, recordCount);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ApiResponse(ApiStatus.SUCCESS, userList));
    }
    
    @PutMapping("/user")
    public ResponseEntity<ApiResponse> updateUser(HttpServletRequest servletRequest, @RequestBody UserUpdateRequest request) {
        Object userIdObject = servletRequest.getAttribute(JwtToken.CLAIMS_USER_ID);
        userService.validateSession(userIdObject, request.id);
        request.validate();
        Long userId = userService.updateUser(request.id, request.name, request.role);
        UserInfo userInfo = userService.getUser(userId);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header("Location", "/api/user/" + userId)
            .body(new ApiResponse(ApiStatus.USER_NEED_RESIGNIN, userInfo));
    }
    
    @PutMapping("/user/credential")
    public ResponseEntity<ApiResponse> updateUserPassword(HttpServletRequest servletRequest, @RequestBody UserPasswordUpdateRequest request) {
        Object userIdObject = servletRequest.getAttribute("userId");
        userService.validateSession(userIdObject, request.id);
        request.validate();
        Long userId = userService.updateUserPassword(request.id, request.password, request.newPassword);
        UserInfo userInfo = userService.getUser(userId);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header("Location", "/api/user/" + userId)
            .body(new ApiResponse(ApiStatus.USER_NEED_RESIGNIN, userInfo));
    }

    @AdminOnly
    @PostMapping("/user/{userId}/credential/reset")
    public ResponseEntity<ApiResponse> resetUserPassword(@RequestBody UserPasswordUpdateRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
    
    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/user/{id}/group/{groupId}")
    public ResponseEntity<ApiResponse> leaveGroup(@PathVariable Long id, @PathVariable Long groupId) {
        userService.leaveGroup(id, groupId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }
}
