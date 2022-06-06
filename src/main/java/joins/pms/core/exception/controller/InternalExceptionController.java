package joins.pms.core.exception.controller;

import joins.pms.core.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class InternalExceptionController implements ErrorController {
    @GetMapping("/error")
    public ResponseEntity handle (HttpServletRequest request) {
        int status = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());
        return ResponseEntity.status(status).build();
    }
}
