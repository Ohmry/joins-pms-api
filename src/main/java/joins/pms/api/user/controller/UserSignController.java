package joins.pms.api.user.controller;

import joins.pms.api.user.model.UserDto;
import joins.pms.api.user.service.UserSignService;
import joins.pms.core.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserSignController {
    private UserSignService userSignService;

    public UserSignController (UserSignService userSignService) {
        this.userSignService = userSignService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signin (HttpServletRequest httpServletRequest, @RequestBody UserDto userDto) {
        return null;
    }

    @DeleteMapping("/signout/{id}")
    public ResponseEntity<ApiResponse> signout (HttpServletRequest httpServletRequest, @PathVariable String id) {
        return null;
    }
}
