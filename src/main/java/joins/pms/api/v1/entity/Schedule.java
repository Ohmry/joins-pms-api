package joins.pms.api.v1.entity;

import joins.pms.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Schedule extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;
}
