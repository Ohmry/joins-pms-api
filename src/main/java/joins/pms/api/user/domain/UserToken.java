package joins.pms.api.user.domain;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;

public class UserToken {
    private static final String SECRET_KEY = "joinsUserToken12#$";
    public static String create (UserInfo userInfo) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("joins-pms-api")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("email", userInfo.getEmail())
                .claim("id", userInfo.getId())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
