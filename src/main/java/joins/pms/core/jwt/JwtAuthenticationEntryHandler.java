package joins.pms.core.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @see <a href="https://velog.io/@shinmj1207/Spring-Spring-Security-JWT-%EB%A1%9C%EA%B7%B8%EC%9D%B8">[Spring] Spring Security + JWT 로그인</a>
 */
public class JwtAuthenticationEntryHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    }
}
