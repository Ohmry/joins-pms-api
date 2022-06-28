package joins.pms.api.v1.board.model;

import joins.pms.api.v1.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class BoardCreateRequest {
    public String title;
    public String description;

    public void validate() {
        if (!StringUtils.hasText(title)) {
            throw new IllegalRequestException();
        }
    }
}
