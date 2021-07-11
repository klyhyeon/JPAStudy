package learn.jpa.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;


    @Builder
    public Team(String name){
        this.name = name;
    }
}
