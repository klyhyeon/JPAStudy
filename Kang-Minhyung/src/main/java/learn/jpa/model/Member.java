package learn.jpa.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Serializable {
    private static final long serialVersionUID = 3990803224604257521L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int age;
    private String address;

    @Builder
    public Member(Long id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public Member(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
