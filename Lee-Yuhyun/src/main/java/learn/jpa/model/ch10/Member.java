package learn.jpa.model.ch10;

import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
public class Member {

    @Builder
    protected Member (String username, int age) {
        this.username = username;
        this.age = age;
    }

    protected Member() {

    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;

    private int age;
}