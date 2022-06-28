package joins.pms.core.jwt;

import io.jsonwebtoken.*;
import joins.pms.api.exception.UnAuthorizationException;
import joins.pms.core.jwt.exception.JwtTokenExpiredException;
import joins.pms.core.jwt.exception.JwtTokenInvalidException;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class JwtToken {
    private static final String SECRET_KEY = "JWT_TOKEN_SECRET_KEY";
    private static final String JWT_PREFIX = "Bearer ";

    private final String accessToken;

    public enum Type {
        accessToken,
        refreshToken
    }

    protected JwtToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static JwtToken valueOf(String authorization) {
        if (!StringUtils.hasText(authorization)) {
            throw new UnAuthorizationException();
        }
        if (!authorization.startsWith(JWT_PREFIX)) {
            throw new JwtTokenInvalidException();
        }
        return new JwtToken(authorization.substring(JWT_PREFIX.length()));
    }

    public void validate() {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(this.accessToken);
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException();
        } catch (Exception e) {
            throw new JwtTokenInvalidException();
        }
    }

    public Claims getClaims() {
        this.validate();
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(this.accessToken)
                .getBody();
    }

    public static String generate(JwtToken.Type type, String email, String clientIp, List<String> roles) {
        switch (type) {
            case accessToken:
                return _generateToken(email, clientIp, roles, 30);
            case refreshToken:
                return _generateToken(email, clientIp, roles, 60 * 12);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static String _generateToken(String email, String clientIp, List<String> roles, int validMinute) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + Duration.ofMinutes(validMinute).toMillis());
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim("email", email)
                .claim("clientIp", clientIp)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }




}
