package joins.pms.api.v1.model.entity;

import joins.pms.core.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_ID")
    private Long id;
    @Column(name = "USR_EMAIL", nullable = false, length = 100, unique = true)
    private String email;
    @Column(name = "USR_PWD")
    private String password;
    @Column(name = "USR_NM", nullable = false, length = 50)
    private String name;
}
