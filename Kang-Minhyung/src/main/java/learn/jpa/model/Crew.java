package learn.jpa.model;

import javax.persistence.*;

@Entity
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    RoleType roleType1;

    @Enumerated(EnumType.STRING)
    RoleType roleType2;

    public Crew() {
    }

    public Crew(RoleType roleType1, RoleType roleType2) {
        this.roleType1 = roleType1;
        this.roleType2 = roleType2;
    }
}


