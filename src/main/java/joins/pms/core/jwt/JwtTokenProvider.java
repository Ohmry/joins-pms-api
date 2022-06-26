package joins.pms.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import joins.pms.core.jwt.exception.JwtTokenInvalidException;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;

public class JwtTokenProvider {
    private static final String SECRET_KEY = "JWT_TOKEN_SECRET_KEY";
    private static final String JWT_PREFIX = "Bearer ";

    public static String generate(JwtType jwtType, String principal) {
        switch (jwtType) {
            case ACCESS_TOKEN:
                return _generateToken(principal, 30);
            case REFRESH_TOKEN:
                return _generateToken(principal, 60 * 12);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static String generateAccessToken(String email) {
        return _generateToken(email, 30);
    }

    public static String generateRefreshToken(String email) {
        return _generateToken(email, 60 * 12);
    }

    public static String getAccessToken(String authorization) {
        if (!StringUtils.hasText(authorization) || !authorization.startsWith(JWT_PREFIX)) {
            throw new JwtTokenInvalidException();
        } else {
            return authorization.substring(JWT_PREFIX.length());
        }
    }

    public static String getPrincipal(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("principal").toString();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String _generateToken(String principal, int validMinute) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + Duration.ofMinutes(validMinute).toMillis());
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim("principal", principal)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
