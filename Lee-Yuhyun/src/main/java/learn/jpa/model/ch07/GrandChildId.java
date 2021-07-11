package learn.jpa.model.ch07;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GrandChildId implements Serializable {

    private ChildId childId; //

    @Column(name = "GRANDCHILD_ID")
    private String id;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
