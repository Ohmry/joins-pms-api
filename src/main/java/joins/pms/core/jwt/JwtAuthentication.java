package joins.pms.core.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class JwtAuthentication extends UsernamePasswordAuthenticationToken {
    public JwtAuthentication(String principal, String credential) {
        super(principal, credential);
    }
    public JwtAuthentication(String principal, String credential, List<GrantedAuthority> authroies) {
        super(principal, credential, authroies);
    }
}
