package joins.pms.api.v1.task.model;

import joins.pms.api.domain.Progress;
import joins.pms.api.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

public class TaskCreateRequest {
    public Long id;
    public String title;
    public String description;
    public String startDateTime;
    public String endDateTime;
    public Progress progress;
    
    public void validate() {
        if (!StringUtils.hasText(title) || id != null) {
            throw new IllegalRequestException();
        }
    }
}
