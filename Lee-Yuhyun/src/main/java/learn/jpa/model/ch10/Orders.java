package learn.jpa.model.ch10;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@NamedEntityGraph(name = "Orders.withMember", attributeNodes = {
        @NamedAttributeNode("member")
})
@Entity
@Getter
public class Orders {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @Builder
    public Orders(Member member) {
        this.member = member;
    }

    public Orders() {

    }
}
