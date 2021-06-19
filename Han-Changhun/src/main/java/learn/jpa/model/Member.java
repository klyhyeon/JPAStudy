package learn.jpa.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Member implements Serializable {
    private static final long serialVersionUID = 3990803224604257521L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    public static Member createMember(String name, int age) {
        return new Member(name, age);
    }

    private Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Builder
    public Member(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
