package joins.pms.api.http.service;

import joins.pms.api.user.model.UserInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 세션과 관련된 기능을 제공하는 서비스
 * @since 2022.07.24
 */
@Service
public class SessionService {
    private final int SESSION_INTERVAL_MINUTE = 30;
    private final String SESSION_USERINFO = "USER_INFO";

    /**
     * 사용자정보를 세션에 저장한다.
     * @param request HttpServletRequest 객체
     * @param userInfo 사용자정보를 담은 VO 객체
     * @since 2022.07.24
     */
    public void setUserInfo(HttpServletRequest request, UserInfo userInfo) {
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_USERINFO, userInfo.toString());
        session.setMaxInactiveInterval(SESSION_INTERVAL_MINUTE);
    }

    /**
     * 사용자정보를 세션에서 가져온다.
     * @param request HttpServletRequest 객체
     * @since 2022.07.24
     */
    public UserInfo getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (UserInfo) session.getAttribute(SESSION_USERINFO);
    }

    /**
     * 사용자정보를 담은 세션을 만료시킨다.
     * @param request HttpServletRequest 객체
     * @since 2022.07.24
     */
    public void expireUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
    }

    /**
     * 사용자정보의 세션유지시간을 연장한다.
     * @param request HttpServletRequest 객체
     * @param userInfo 사용자정보를 담은 VO 객체
     * @since 2022.07.24
     */
    public void extendUserInfo(HttpServletRequest request, UserInfo userInfo) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(SESSION_INTERVAL_MINUTE);
    }
}
