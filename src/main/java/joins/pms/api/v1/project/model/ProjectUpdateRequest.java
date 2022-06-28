package joins.pms.api.v1.project.model;

import joins.pms.api.v1.exception.IllegalRequestException;
import joins.pms.core.domain.Progress;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class ProjectUpdateRequest {
    public Long id;
    public String title;
    public String descriptoin;
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
    public Progress progress;

    public void validate() {
        if (id == null || id < 1 || !StringUtils.hasText(title) || startDateTime == null || endDateTime == null || progress == null) {
            throw new IllegalRequestException();
        }
    }
}
