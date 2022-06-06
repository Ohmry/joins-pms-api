package joins.pms.core.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieService {
    public String getCookieValue (HttpServletRequest httpServletRequest, String cookieName) {
        String value = null;
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (cookie.getName().equals(cookieName)) {
                value = cookie.getValue();
            }
        }
        return value;
    }
}
