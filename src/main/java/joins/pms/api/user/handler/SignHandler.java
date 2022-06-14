package joins.pms.api.user.handler;

import joins.pms.api.user.model.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SignHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private final int SESSION_INTERVAL;

    public SignHandler (@Value("${application.session.interval}") int sessionInterval) {
        this.SESSION_INTERVAL = sessionInterval;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        request.getRequestDispatcher("signin/fail").forward(request, response);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDto userDto = (UserDto) authentication.getPrincipal();
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("email", userDto.getEmail());
        httpSession.setAttribute("name", userDto.getName());
        httpSession.setAttribute("role", userDto.getUserRole());
        httpSession.setMaxInactiveInterval(60 * SESSION_INTERVAL);
        request.getRequestDispatcher("signin/success").forward(request, response);
    }
}
