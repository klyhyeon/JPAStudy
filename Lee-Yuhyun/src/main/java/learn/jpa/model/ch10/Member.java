package learn.jpa.model.ch10;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString(exclude = "team")
@Getter
//@NamedQuery(name = "Member.findByUsername", query="select m from Member m where m.username = :username")
@NamedQueries({
        @NamedQuery(name = "Member.findByUsername", query="select m from Member m where m.username = :username"),
        @NamedQuery(name = "Member.count", query="select count(m) from Member m")
})
public class Member {

    @Builder
    protected Member (String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }

    protected Member() {

    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;

    private int age;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Team team;

}