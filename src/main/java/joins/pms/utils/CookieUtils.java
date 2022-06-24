package joins.pms.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CookieUtils {
    /**
     * Header에 포함된 쿠키 중에서 cookieName에 해당하는 값을 가져온다.
     * @param request request
     * @param cookieName 쿠키 이름
     * @return 쿠키 이름에 해당하는 값
     */
    public static String getCookieValue (HttpServletRequest request, String cookieName) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .orElse(null)
                .getValue();
    }
}
