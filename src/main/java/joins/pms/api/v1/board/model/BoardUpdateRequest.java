package joins.pms.api.v1.board.model;

import joins.pms.api.v1.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class BoardUpdateRequest {
    public Long id;
    public String title;
    public String description;

    public void validate() {
        if (id == null || id < 1 || !StringUtils.hasText(title)) {
            throw new IllegalRequestException();
        }
    }
}
