package joins.pms.api.user.model;

import joins.pms.core.domain.BaseDataTransferObject;
import org.springframework.util.StringUtils;

/**
 * 사용자의 비밀번호를 변경하기 위한 요청 객체
 */
public class PasswordUpdateRequest extends BaseDataTransferObject {
    public Long id;
    public String password;
    public String newPassword;

    @Override
    public BaseDataTransferObject checkParameterValidation() {
        if (id == null || id < 1 || !StringUtils.hasText(password) || !StringUtils.hasText(newPassword)) {
            throw new IllegalArgumentException();
        }
        return this;
    }

    @Override
    public BaseDataTransferObject checkSameUser(Long id) {
        if (!this.id.equals(id)) {
            throw new IllegalArgumentException();
        }
        return this;
    }
}
