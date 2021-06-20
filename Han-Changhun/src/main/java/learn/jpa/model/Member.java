package learn.jpa.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Member implements Serializable {
    private static final long serialVersionUID = 3990803224604257521L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int age;

    @Builder(toBuilder = true)
    public static Member createMember(String name, int age) {
        return new Member(name, age);
    }
}
