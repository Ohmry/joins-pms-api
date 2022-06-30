package joins.pms.core.jwt;

import io.jsonwebtoken.*;
import joins.pms.api.exception.UnauthorizationException;
import joins.pms.core.jwt.exception.JwtTokenExpiredException;
import joins.pms.core.jwt.exception.JwtTokenInvalidException;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class JwtToken {
    public static final String CLAIMS_EMAIL = "email";
    public static final String CLAIMS_CLINETIP = "clientIp";
    public static final String CLAIMS_ROLES = "roles";
    public static final String CLAIMS_USER_ID = "userId";
    
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
            throw new UnauthorizationException();
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

    public String value() {
        return this.accessToken;
    }
    
    public Claims getClaims() {
        this.validate();
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(this.accessToken)
            .getBody();
    }

    public static Claims getClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException();
        } catch (Exception e) {
            throw new JwtTokenInvalidException();
        }
    }
    
    public static String generate(JwtToken.Type type, Long id, String email, String clientIp, String userRole) {
        switch (type) {
            case accessToken:
                return _generateToken(id, email, clientIp, userRole, 30);
            case refreshToken:
                return _generateToken(id, email, clientIp, userRole, 60 * 12);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static String generate(JwtToken.Type type, Claims claims) {
        Long id = Long.parseLong(claims.get(CLAIMS_USER_ID).toString());
        String email = claims.get(CLAIMS_EMAIL).toString();
        String clientIp = claims.get(CLAIMS_CLINETIP).toString();
        String userRole = claims.get(CLAIMS_ROLES).toString();

        return generate(type, id, email, clientIp, userRole);
    }
    
    private static String _generateToken(Long id, String email, String clientIp, String userRole, int validMinute) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + Duration.ofMinutes(validMinute).toMillis());
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuedAt(now)
            .setExpiration(expireDate)
            .claim(CLAIMS_USER_ID, id)
            .claim(CLAIMS_EMAIL, email)
            .claim(CLAIMS_CLINETIP, clientIp)
            .claim(CLAIMS_ROLES, userRole)
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }
}
