package joins.pms.api.user.model;

import joins.pms.api.v1.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

/**
 * 사용자의 비밀번호를 변경하기 위한 요청 객체
 */
public class PasswordUpdateRequest {
    public Long id;
    public String password;
    public String newPassword;

    public void validate() {
        if (id == null || id < 1 || !StringUtils.hasText(password) || !StringUtils.hasText(newPassword)) {
            throw new IllegalRequestException();
        }
    }
}
