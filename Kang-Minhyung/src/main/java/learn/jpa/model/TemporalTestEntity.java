package learn.jpa.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TemporalTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date1;

    @Temporal(TemporalType.TIME)
    private Date date2;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date3;

    public TemporalTestEntity() {
    }

    public TemporalTestEntity(Date date1, Date date2, Date date3) {
        this.date1 = date1;
        this.date2 = date2;
        this.date3 = date3;
    }
}
