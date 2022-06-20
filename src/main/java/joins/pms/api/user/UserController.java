package joins.pms.api.user;

import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * 사용자(User) 관련 컨트롤러
 *
 * @see UserService
 * @version 1.0
 * @author Ohmry
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> findById (@PathVariable UUID id) {
        UserInfo userInfo = userService.find(id);
        ApiResponse response;
        if (userInfo == null) {
            response = new ApiResponse(ApiStatus.DATA_IS_EMPTY, null);
        } else {
            response = new ApiResponse(ApiStatus.SUCCESS, userInfo);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<ApiResponse> update (HttpServletRequest request, @PathVariable UUID id, @RequestBody UserUpdateRequest userUpdateRequest) throws URISyntaxException {
        HttpSession httpSession = request.getSession();
        String sessionUserId = httpSession.getAttribute("id").toString();

        if (!id.toString().equals(sessionUserId)) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.ABNORMAL_ACCESS));
        }

        if (!StringUtils.hasText(userUpdateRequest.getName()) || !StringUtils.hasText(userUpdateRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.IDENTIFY_NEEDS_NOT_EMPTY));
        }

        try {
            String userId = userService.update(id, userUpdateRequest);
            return ResponseEntity.created(new URI("/user/" + userId)).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse> leave (HttpServletRequest request, @PathVariable UUID id) {
        HttpSession httpSession = request.getSession();
        String sessionUserId = httpSession.getAttribute("id").toString();

        if (!id.toString().equals(sessionUserId)) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.ABNORMAL_ACCESS));
        }

        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
