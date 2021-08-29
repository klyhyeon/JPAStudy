package learn.jpa.model.ch14;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

public class DuckListener {

    @PrePersist
    public void prePersist(Object object) {
        System.out.println("Duck's prePersist id: " + object.toString());
    }

    @PostPersist
    public void postPersist(Object object) {
        System.out.println("Duck's postPersist id: " + object.toString());
    }
}
