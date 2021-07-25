package learn.jpa.model.ch10;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team",fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    public Team() {

    }
    @Builder
    public Team(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

}
