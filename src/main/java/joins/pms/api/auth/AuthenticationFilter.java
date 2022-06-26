package joins.pms.api.auth;

import joins.pms.api.user.service.UserService;
import joins.pms.core.jwt.JwtAuthentication;
import joins.pms.core.jwt.JwtTokenProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final List<String> excludeUris;

    public AuthenticationFilter(UserService userService) {
        this.userService = userService;
        this.excludeUris = Arrays.asList("/api/signin", "/api/signup", "/console");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = JwtTokenProvider.getAccessToken(request.getHeader("Authorization"));
        JwtTokenProvider.validateToken(accessToken);
        String userEmail = JwtTokenProvider.getPrincipal(accessToken);
        userService.verifyToken(userEmail, accessToken);

        JwtAuthentication authentication = new JwtAuthentication(userEmail, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return excludeUris.stream()
                .anyMatch(uri -> uri.equalsIgnoreCase(request.getRequestURI()));
    }
}
