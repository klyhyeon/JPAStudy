package learn.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Team {

    @Id @Column(name = "TEAM_ID")
    private String id;
    private String name;
}
