package learn.jpa.model.ch04;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = {"NAME", "AGE"})})
public class MemberCh4 extends TimeEntity {

    @Builder
    public MemberCh4 (String id) {
        this.id = id;
    }

    @Id
    private String id;

    @Column(name = "NAME", nullable = false, length = 10)
    private String username;

    private int age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
