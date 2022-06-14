package joins.pms.api.user.controller;

import joins.pms.api.user.model.UserDto;
import joins.pms.api.user.model.UserRole;
import joins.pms.api.user.service.SignService;
import joins.pms.core.api.ApiResponse;
import joins.pms.core.api.ApiStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class SignController implements AuthenticationProvider {
    private final SignService signService;

    public SignController (SignService signService) {
        this.signService = signService;
    }

    @PostMapping("/signup")
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
        UUID id = signService.signup(userDto);
        return ResponseEntity.created(new URI("/api/sigin")).build();
    }

    @PostMapping("/signin/fail")
    public ResponseEntity<ApiResponse> fail () {
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.FAILED_TO_LOGIN));
    }

    @PostMapping("/signin/success")
    public ResponseEntity<ApiResponse> success (HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponse(ApiStatus.SUCCESS, httpSession.getAttribute("email")));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDto userDto = signService.signin(email, password);
        List<GrantedAuthority> authroies = new ArrayList<>();
        authroies.add(new SimpleGrantedAuthority("ROLE_" + UserRole.USER));
        return new UsernamePasswordAuthenticationToken(userDto, null, authroies);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
