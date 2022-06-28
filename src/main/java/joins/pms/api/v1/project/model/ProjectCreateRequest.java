package joins.pms.api.v1.project.model;

import joins.pms.api.domain.Progress;
import joins.pms.api.exception.IllegalRequestException;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ProjectCreateRequest {
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
    
        try {
            if (startDateTime != null) {
                LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            }
            if (endDateTime != null) {
                LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            }
        } catch (DateTimeParseException e) {
            throw new IllegalRequestException(e);
        }
        
    }
}
