package joins.pms.api.user.controller;

import joins.pms.api.user.model.UserDto;
import joins.pms.api.user.service.UserService;
import joins.pms.core.api.ApiResponse;
import joins.pms.core.api.ApiStatus;
import joins.pms.core.model.converter.ModelConverter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final ModelConverter modelConverter;
    private final String API_URL = "/user";

    public UserController (UserService userService,
                           ModelConverter modelConverter) {
        this.userService = userService;
        this.modelConverter = modelConverter;
    }

    @PostMapping(API_URL)
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
    @GetMapping(API_URL + "/{id}")
    public ResponseEntity<ApiResponse> findById (@PathVariable UUID id) {
        UserDto userDto = userService.find(id);
        ApiResponse response;
        if (userDto == null) {
            response = new ApiResponse(ApiStatus.DATA_IS_EMPTY, null);
        } else {
            Map userMap = modelConverter.convert(userDto, Map.class);
            userMap.remove("password");
            response = new ApiResponse(ApiStatus.SUCCESS, userMap);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PutMapping(API_URL)
    public ResponseEntity<ApiResponse> update (@RequestBody UserDto userDto) throws URISyntaxException {
        if (userDto.getId() == null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.IDENTIFY_NEEDS_NOT_EMPTY));
        }
        UUID id = userService.save(userDto);
        return ResponseEntity.created(new URI(API_URL + "/" + id)).build();
    }

    @DeleteMapping(API_URL)
    public ResponseEntity<ApiResponse> leave (@RequestBody UserDto userDto) {
        if (userDto.getId() == null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponse(ApiStatus.IDENTIFY_NEEDS_NOT_EMPTY));
        }
        userService.delete(userDto.getId());
        return ResponseEntity.noContent().build();
    }
}
