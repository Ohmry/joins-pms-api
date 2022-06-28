package joins.pms.api.v1.task.model;

import joins.pms.api.domain.Progress;
import joins.pms.api.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TaskUpdateRequest {
    public Long id;
    public String title;
    public String description;
    public String startDateTime;
    public String endDateTime;
    public Progress progress;
    
    public void validate() {
        if (id == null || id < 1 || !StringUtils.hasText(title) || startDateTime == null || endDateTime == null | progress == null) {
            throw new IllegalRequestException();
        }
    
        try {
            LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        } catch (DateTimeParseException e) {
            throw new IllegalRequestException(e);
        }
    }
}
