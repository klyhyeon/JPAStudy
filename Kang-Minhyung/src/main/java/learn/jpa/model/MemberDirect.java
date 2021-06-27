package learn.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MemberDirect {
    @Id
    private Long id;

    public MemberDirect() {
    }

    public MemberDirect(Long id) {
        this.id = id;
    }
}
