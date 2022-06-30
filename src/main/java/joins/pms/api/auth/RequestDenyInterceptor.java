package joins.pms.api.auth;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestDenyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("requestUri", request.getRequestURI());
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.UNAUTHORIZED.value());
        request.getRequestDispatcher("/error").forward(request, response);
        return false;
    }
}
