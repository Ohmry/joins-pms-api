package joins.pms.api.v1.controller;

import joins.pms.api.v1.model.dto.UserDto;
import joins.pms.api.v1.service.UserService;
import joins.pms.core.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ApiResponse signin (@RequestBody UserDto userDto) {

        return new ApiResponse(HttpStatus.OK, null);
    }

    @PostMapping("/signup")
    public ApiResponse signup (@RequestBody UserDto userDto) {
        return new ApiResponse(HttpStatus.OK, null);
    }

    @PostMapping("/signout")
    public ApiResponse signout (Long id) {
        return new ApiResponse(HttpStatus.OK, null);
    }

    @DeleteMapping("/dropout")
    public ApiResponse dropout (Long id) {
        return new ApiResponse(HttpStatus.OK, null);
    }
}
