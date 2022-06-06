package joins.pms.user.controller;

import joins.pms.core.api.ApiResponse;
import joins.pms.core.api.ApiStatus;
import joins.pms.user.model.code.UserStatus;
import joins.pms.user.model.dto.UserDto;
import joins.pms.user.model.dto.UserSessionDto;
import joins.pms.user.model.entity.UserSession;
import joins.pms.user.service.UserService;
import joins.pms.user.service.UserSessionService;
import org.apache.tomcat.jni.Local;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserSessionService userSessionService;
    private final String API_URL = "/user";

    public UserController (UserService userService,
                           UserSessionService userSessionService) {
        this.userService = userService;
        this.userSessionService = userSessionService;
    }
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signin (HttpServletRequest httpServletRequest, @RequestBody UserDto userDto) {
        if (!userService.signin(userDto)) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.FAILED_TO_LOGIN));
        }

        String jsessionId = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (cookie.getName().equals("JSESSIONID")) {
                jsessionId = cookie.getValue();
            }
        }

        UserSessionDto userSessionDto = new UserSessionDto();
        userSessionDto.setId(userDto.getId());
        userSessionDto.setSession(jsessionId);
        userSessionDto.setExpireDateTime(LocalDateTime.now().plusMinutes(30));
        LocalDateTime expireDateTime = userSessionService.save(userSessionDto);

        ApiResponse response = new ApiResponse(ApiStatus.SUCCESS, expireDateTime);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
    @PostMapping("/" + API_URL + "/signout")
    public ResponseEntity<ApiResponse> signout (Long id) {
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<ApiResponse> signup (@RequestBody UserDto userDto) throws URISyntaxException {
        UUID id = userService.save(userDto);
        return ResponseEntity.created(new URI(API_URL + "/" + id)).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById (@PathVariable UUID id) {
        UserDto userDto = userService.findById(id);
        ApiResponse response = new ApiResponse(userDto == null ? ApiStatus.DATA_IS_EMPTY : ApiStatus.SUCCESS, userDto);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
