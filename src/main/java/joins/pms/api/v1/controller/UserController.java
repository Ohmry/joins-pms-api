package joins.pms.api.v1.controller;

import joins.pms.api.v1.model.dto.UserDto;
import joins.pms.api.v1.service.UserService;
import joins.pms.core.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity signin (@RequestBody UserDto userDto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity signup (@RequestBody UserDto userDto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signout")
    public ResponseEntity signout (Long id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/dropout")
    public ResponseEntity dropout (Long id) {
        return ResponseEntity.ok().build();
    }
}
