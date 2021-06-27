package learn.jpa.model;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class AccessTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data1;
    private String data2;
}


