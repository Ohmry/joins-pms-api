package joins.pms.api.user.controller;

import joins.pms.api.user.domain.UserInfo;
import joins.pms.api.user.exception.AlreadyEmailExistsException;
import joins.pms.api.user.model.PasswordUpdateRequest;
import joins.pms.api.user.model.SignupRequest;
import joins.pms.api.user.model.UserUpdateRequest;
import joins.pms.api.user.service.UserService;
import joins.pms.core.exception.InternalExceptionHandler;
import joins.pms.core.http.SpringSecurityConfigure;
import joins.pms.core.http.ApiResponse;
import joins.pms.core.http.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 사용자관련 Controller 객체
 * <p>UserInterface 레이어에 존재한다.</p>
 */
@RestController
@RequestMapping("/api")
public class UserController implements AuthenticationProvider {
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
        request.checkParameterValidation();
        userService.checkEmailIsDuplicated(request.email);
        UserInfo userInfo = userService.createUser(request);
        return ResponseEntity
                .created(new URI("/api/user/" + userInfo.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.SUCCESS, userInfo));
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
        request.checkParameterValidation();
        request.checkSameUser(id);
        UserInfo userInfo = userService.updateUser(id, request);
        return ResponseEntity
                .created(new URI("/api/user/" + userInfo.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.SUCCESS, userInfo));
    }

    @PutMapping("/user/{id}/credential")
    public ResponseEntity<ApiResponse> updateUserPassword(@PathVariable Long id, @RequestBody PasswordUpdateRequest request) {
        request.checkParameterValidation();
        request.checkSameUser(id);
        userService.updateUserPassword(id, request);
        return ResponseEntity
                .noContent()
                .build();
    }

    /**
     * SpringSecurity에서 설정한 로그인의 인증을 구현한 메소드
     * <p>
     * {@link SpringSecurityConfigure}에서 loginProcessingUrl 인자에 입력한 주소로 POST 요청을 받을 때,
     * 실제 로그인을 허용할 것인지 허용하지 않을 것인지 검증 로직을 구현하는 부분이다.
     * {@link AuthenticationProvider}를 상속받아서 메소드를 Override하여 구현하였다.
     * </p>
     *
     * @param authentication 인증 요청을 위해 받은 객체정보 (name: email, credentials: password)
     * @return {@link UsernamePasswordAuthenticationToken}
     * @throws AuthenticationException 사용자 인증에 실패했을 때 발생하는 예외
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserInfo userInfo = userService.signin(email, password);
        return new UsernamePasswordAuthenticationToken(userInfo, null, null);
    }

    @PostMapping("/signin/fail")
    public ResponseEntity<ApiResponse> fail() {
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.FAILED_LOGIN));
    }

    @PostMapping("/signin/success")
    public ResponseEntity<ApiResponse> success(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.SUCCESS, httpSession.getAttribute("email")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
