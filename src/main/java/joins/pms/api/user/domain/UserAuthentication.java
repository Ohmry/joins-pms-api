package joins.pms.api.user.domain;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {
    public UserAuthentication(String principal, String credential) {
        super(principal, credential);
    }
    public UserAuthentication(String principal, String credential, List<GrantedAuthority> authroies) {
        super(principal, credential, authroies);
    }
}
