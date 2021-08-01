package learn.jpa.model.ch10;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Orders {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Member member;

    @Builder
    public Orders(Member member) {
        this.member = member;
    }

    public Orders() {

    }
}
