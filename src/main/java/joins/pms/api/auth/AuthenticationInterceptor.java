package joins.pms.api.auth;

import io.jsonwebtoken.Claims;
import joins.pms.api.exception.InvalidPermissionException;
import joins.pms.api.exception.UnauthorizationException;
import joins.pms.api.http.ApiStatus;
import joins.pms.api.user.service.UserService;
import joins.pms.core.annotations.AdminOnly;
import joins.pms.core.annotations.LocalOnly;
import joins.pms.core.jwt.JwtToken;
import joins.pms.core.jwt.exception.JwtTokenExpiredException;
import joins.pms.core.jwt.exception.JwtTokenInvalidException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final UserService userService;

    public AuthenticationInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        try {
            request.setAttribute("requestUri", requestUri);
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            JwtToken jwtToken = JwtToken.valueOf(authorization);
            jwtToken.validate();

            Claims claims = jwtToken.getClaims();
            Long userId = claims.get(JwtToken.CLAIMS_USER_ID, Long.class);
            String email = claims.get(JwtToken.CLAIMS_EMAIL, String.class);
            String clientIp = claims.get(JwtToken.CLAIMS_CLINETIP, String.class);
            String userRole = claims.get(JwtToken.CLAIMS_ROLES, String.class);

            userService.validateToken(userId, jwtToken.value());
    
            HandlerMethod method = (HandlerMethod) handler;
            LocalOnly localOnly = method.getMethodAnnotation(LocalOnly.class);
            if (localOnly != null && clientIp.equals("127.0.0.1")) {
                throw new InvalidPermissionException();
            }
            
            AdminOnly adminOnly = method.getMethodAnnotation(AdminOnly.class);
            if (adminOnly != null && !userRole.equals("ADMIN")) {
                throw new InvalidPermissionException();
            }

            request.setAttribute("userEmail", email);
            request.setAttribute("userId", userId);
            request.setAttribute("userRoles", userRole);
            return true;
        } catch (JwtTokenInvalidException e) {
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.UNAUTHORIZED.value());
            request.setAttribute("apiStatus", ApiStatus.JWT_TOKEN_INVALID);
            request.getRequestDispatcher("/error").forward(request, response);
            return false;
        } catch (JwtTokenExpiredException e) {
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.UNAUTHORIZED.value());
            request.setAttribute("apiStatus", ApiStatus.JWT_TOKEN_EXPIRED);
            request.getRequestDispatcher("/error").forward(request, response);
            return false;
        } catch (InvalidPermissionException e) {
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.FORBIDDEN.value());
            request.setAttribute("apiStatus", ApiStatus.INVALID_PERMISSION);
            request.getRequestDispatcher("/error").forward(request, response);
            return false;
        } catch (Exception e) {
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.FORBIDDEN.value());
            request.setAttribute("apiStatus", ApiStatus.INVALID_PERMISSION);
            request.getRequestDispatcher("/error").forward(request, response);
            return false;
        }
    }
}
