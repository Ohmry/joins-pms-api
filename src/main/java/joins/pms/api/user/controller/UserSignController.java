package joins.pms.api.user.controller;

import joins.pms.api.user.exception.NotSuitableUserException;
import joins.pms.api.user.exception.UserNotFoundException;
import joins.pms.api.user.model.UserDto;
import joins.pms.api.user.model.UserSessionDto;
import joins.pms.api.user.service.UserSessionService;
import joins.pms.api.user.service.UserSignService;
import joins.pms.core.api.ApiResponse;
import joins.pms.core.api.ApiStatus;
import joins.pms.core.service.CookieService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user")
public class UserSignController {
    private final UserSignService userSignService;
    private final UserSessionService userSessionService;
    private final CookieService cookieService;

    public UserSignController (UserSignService userSignService,
                               UserSessionService userSessionService,
                               CookieService cookieService) {
        this.userSignService = userSignService;
        this.userSessionService = userSessionService;
        this.cookieService = cookieService;
    }

    @Transactional
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signin (HttpServletRequest httpServletRequest, @RequestBody UserDto userDto) {
        String jSessionId = cookieService.getCookieValue(httpServletRequest, "JSESSIONID");
        if (jSessionId == null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.ABNORMAL_ACCESS));
        }

        try {
            userDto = userSignService.authorize(userDto);
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("id", userDto.getId());
            httpSession.setAttribute("name", userDto.getName());
            httpSession.setAttribute("email", userDto.getEmail());
            httpSession.setAttribute("userRole", userDto.getUserRole());
            httpSession.setMaxInactiveInterval(60 * 30);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.SUCCESS));
        } catch (NotSuitableUserException | UserNotFoundException e) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.FAILED_TO_LOGIN));
        }
    }

    @DeleteMapping("/signout/{id}")
    public ResponseEntity<ApiResponse> signout (HttpServletRequest httpServletRequest, @PathVariable String id) {
        HttpSession httpSession = httpServletRequest.getSession();
        if (httpSession.isNew()) {
            return ResponseEntity.notFound()
                    .build();
        } else if (!httpSession.getAttribute("id").equals(id)) {
            return ResponseEntity.badRequest()
                    .build();
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.SUCCESS));
        }
    }
}
