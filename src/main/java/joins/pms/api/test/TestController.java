package joins.pms.api.test;

import joins.pms.api.http.ApiResponse;
import joins.pms.api.http.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {
    @PostMapping("/test")
    public ResponseEntity<ApiResponse> helloWorld(@RequestBody String message) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.SUCCESS, message));
    }
}
