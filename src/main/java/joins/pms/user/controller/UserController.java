package joins.pms.user.controller;

import joins.pms.core.api.ApiResponse;
import joins.pms.core.api.ApiStatus;
import joins.pms.core.service.CookieService;
import joins.pms.user.model.dto.UserDto;
import joins.pms.user.model.dto.UserSessionDto;
import joins.pms.user.service.UserService;
import joins.pms.user.service.UserSessionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserSessionService userSessionService;
    private final CookieService cookieService;
    private final String API_URL = "/user";

    public UserController (UserService userService,
                           UserSessionService userSessionService,
                           CookieService cookieService) {
        this.userService = userService;
        this.userSessionService = userSessionService;
        this.cookieService = cookieService;
    }
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signin (HttpServletRequest httpServletRequest, @RequestBody UserDto userDto) {
        userDto = userService.signin(userDto);
        if (userDto == null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.FAILED_TO_LOGIN));
        }

        String jsessionId = cookieService.getCookieValue(httpServletRequest, "JSESSIONID");
        if (jsessionId == null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.ABNORMAL_ACCESS));
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
    public ResponseEntity<ApiResponse> signout (UUID id) {
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<ApiResponse> signup (@RequestBody UserDto userDto) throws URISyntaxException {
        if (userDto.getEmail().isEmpty() || userDto.getPassword().isEmpty()) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.REQUIRED_PARAMETER_IS_NOT_FOUND));
        } else if (userDto.getId() != null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.IDENTIFY_NEEDS_EMPTY));
        }
        UUID id = userService.save(userDto);
        return ResponseEntity.created(new URI(API_URL + "/" + id)).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById (@PathVariable UUID id) {
        UserDto userDto = userService.findById(id);
        ApiResponse response;
        if (userDto == null) {
            response = new ApiResponse(ApiStatus.DATA_IS_EMPTY, null);
        } else {
            userDto.setPassword("");
            response = new ApiResponse(ApiStatus.SUCCESS, userDto);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
