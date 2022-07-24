package joins.pms.api.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("사용자 정보를 찾을 수 없습니다. 사용자 이메일 주소: " + email);
    }
    public UserNotFoundException(Long userId) {
        super("사용자 정보를 찾을 수 없습니다. 사용자 아이디: " + userId);
    }
}
