package learn.jpa.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class Chair {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int count;

    @Builder
    public Chair(Long id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
}
