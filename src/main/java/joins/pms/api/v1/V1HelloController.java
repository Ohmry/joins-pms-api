package joins.pms.api.v1;

import joins.pms.core.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class V1HelloController {

    @RequestMapping("/")
    public ApiResponse Hello () {
        return new ApiResponse(HttpStatus.OK, "Welcome to server!");
    }
}
