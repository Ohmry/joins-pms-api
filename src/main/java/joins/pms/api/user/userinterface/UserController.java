package joins.pms.api.user.userinterface;

import joins.pms.api.user.application.UserService;
import joins.pms.api.user.domain.SignupRequest;
import joins.pms.api.user.domain.UserInfo;
import joins.pms.http.ApiResponse;
import joins.pms.http.ApiStatus;
import org.springframework.dao.DataIntegrityViolationException;
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

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup (@RequestBody SignupRequest signupRequest) throws URISyntaxException {
        try {
            UserInfo userInfo = userService.createUser(signupRequest);
            if (userInfo == null) {
                return ResponseEntity.internalServerError().body(new ApiResponse(ApiStatus.FAILED_SIGNUP));
            } else {
                return ResponseEntity.created(new URI("/api/user/" + userInfo.getId())).body(new ApiResponse(ApiStatus.SUCCESS, userInfo));
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(ApiStatus.EMAIL_IS_DUPLICATED));
        }
    }

    @PostMapping("/signin/fail")
    public ResponseEntity<ApiResponse> fail () {
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.FAILED_LOGIN));
    }

    @PostMapping("/signin/success")
    public ResponseEntity<ApiResponse> success (HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.SUCCESS, httpSession.getAttribute("email")));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getAllUserInfo () {
        List<UserInfo> userList = userService.getAllUserInfo();
        return ResponseEntity.ok(new ApiResponse(ApiStatus.SUCCESS, userList));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserInfo (@PathVariable Long id) {
        UserInfo userInfo = userService.getUserInfo(id);
        if (userInfo == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new ApiResponse(ApiStatus.SUCCESS, userInfo));
        }
    }

    /**
     * SpringSecurity에서 설정한 로그인의 인증을 구현한 메소드
     * <p>
     *     {@link joins.pms.http.SpringSecurityConfigure}에서 loginProcessingUrl 인자에 입력한 주소로 POST 요청을 받을 때,
     *     실제 로그인을 허용할 것인지 허용하지 않을 것인지 검증 로직을 구현하는 부분이다.
     *     {@link AuthenticationProvider}를 상속받아서 메소드를 Override하여 구현하였다.
     * </p>
     * @param authentication 인증 요청을 위해 받은 객체정보 (name: email, credentials: password)
     * @return {@link UsernamePasswordAuthenticationToken}
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserInfo userInfo = userService.signin(email, password);
        return new UsernamePasswordAuthenticationToken(userInfo, null, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
