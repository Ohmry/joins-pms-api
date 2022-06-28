package joins.pms.api.v1.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Duration {
    @Column
    private LocalDateTime startDateTime;
    @Column
    private LocalDateTime endDateTime;
    
    public enum Field {
        startDateTime,
        endDateTime
    }
    
    public Duration() {
    }
    
    public Duration(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime now = LocalDateTime.now();
        this.startDateTime = startDateTime == null ? now : startDateTime;
        this.endDateTime = endDateTime == null ? now : endDateTime;
    }
    
    public void update(Duration.Field field, Object value) {
        switch (field) {
            case startDateTime:
                this.startDateTime = (LocalDateTime) value;
                break;
            case endDateTime:
                this.endDateTime = (LocalDateTime) value;
                break;
            default:
                break;
        }
    }
    
    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }
    
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }
}
