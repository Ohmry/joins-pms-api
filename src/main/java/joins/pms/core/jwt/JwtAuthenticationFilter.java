package joins.pms.core.jwt;

import joins.pms.core.http.SpringSecurityConfigure;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @see <a href="https://velog.io/@shinmj1207/Spring-Spring-Security-JWT-%EB%A1%9C%EA%B7%B8%EC%9D%B8">[Spring] Spring Security + JWT 로그인</a>
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = _getJwtToken(request);
        _validateToken(token);

        String userObject = JwtTokenProvider.getSubject(token);
        JwtAuthentication jwtAuthentication = new JwtAuthentication(userObject, null, null);
        jwtAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);

        filterChain.doFilter(request, response);
    }

    private String _getJwtToken(HttpServletRequest request) {
        final String TOKEN_PREFIX = "Bearer ";
        String bearerToken = request.getHeader("Authroization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        } else {
            return null;
        }
    }

    private void _validateToken(String token) {
        if (StringUtils.hasText(token)) {
            throw new JwtTokenInvalidException();
        } else if (!JwtTokenProvider.validateToken(token)) {
            throw new JwtTokenExpiredException();
        }
    }
}
