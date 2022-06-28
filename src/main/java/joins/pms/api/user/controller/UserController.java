package joins.pms.api.user.controller;

import joins.pms.api.user.domain.UserInfo;
import joins.pms.api.user.model.*;
import joins.pms.api.user.service.UserService;
import joins.pms.api.exception.InternalExceptionHandler;
import joins.pms.core.http.ApiResponse;
import joins.pms.core.http.ApiStatus;
import joins.pms.core.jwt.JwtTokenProvider;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 사용자관련 Controller 객체
 * <p>
 * UserInterface 레이어에 존재한다.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자의 정보를 새로 생성한다.
     * <p>
     * {@link SignupRequest} 객체를 통해 사용자의 정보를 전달받아서 처리한다.
     * 파라미터의 유효성을 검사하고, 이메일이 중복되지 않았는지 확인한다.
     * 각 함수에서 발생하는 예외는 {@link InternalExceptionHandler}에서 처리한다.
     * </p>
     * @see InternalExceptionHandler
     * @param request 사용자 생성요청 객체
     * @return 생성된 사용자정보
     * @throws URISyntaxException 응답에서 Location에 대한 값이 제대로 만들어지지 않을 경우 발생
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignupRequest request) throws URISyntaxException {
        request.validate();
        userService.checkExistsEmail(request.email);
        UserInfo userInfo = userService.createUser(request);
        return ResponseEntity
                .created(new URI("/api/user/" + userInfo.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.SUCCESS, userInfo));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signin(@RequestBody SigninRequest request) {
        request.validate();
        SignResponse response = userService.signin(request.email, request.password);
        return ResponseEntity
                .ok(new ApiResponse(ApiStatus.SUCCESS, response));
    }

    @PostMapping("/resign")
    public ResponseEntity<ApiResponse> resign(HttpServletRequest servletRequest, @RequestBody ResignRequest request) {
        request.validate();
        String accessToken = JwtTokenProvider.getAccessToken(servletRequest.getHeader("Authorization"));
        SignResponse response = userService.resign(accessToken, request.refreshToken);
        return ResponseEntity
                .ok(new ApiResponse(ApiStatus.SUCCESS, response));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getAllUserInfo() {
        List<UserInfo> userList = userService.getAllUserInfo();
        return ResponseEntity.ok(new ApiResponse(ApiStatus.SUCCESS, userList));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserInfo(@PathVariable Long id) {
        UserInfo userInfo = userService.getUserInfo(id);
        return ResponseEntity.ok(new ApiResponse(ApiStatus.SUCCESS, userInfo));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<ApiResponse> updateUserInfo(@PathVariable Long id, @RequestBody UserUpdateRequest request) throws URISyntaxException {
        request.validate();
        UserInfo userInfo = userService.updateUser(id, request);
        return ResponseEntity
                .created(new URI("/api/user/" + userInfo.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.SUCCESS, userInfo));
    }

    @PutMapping("/user/{id}/credential")
    public ResponseEntity<ApiResponse> updateUserPassword(@PathVariable Long id, @RequestBody PasswordUpdateRequest request) {
        request.validate();
        userService.updateUserPassword(id, request);
        userService.resetToken(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
