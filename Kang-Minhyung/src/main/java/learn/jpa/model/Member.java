package learn.jpa.model;

import lombok.*;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedQuery(
    name = "Member.findByName",
    query = "select m from Member m where m.name = :name"
)
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @OneToMany(mappedBy = "member")
    private List<Orders> orders;

    public void mapTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    @Builder
    public Member(Long id, String name, int age, Team team, List<Orders> orders) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.team = team;
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Member{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + ", teamId=" + team.getId() + '}';
    }
}
