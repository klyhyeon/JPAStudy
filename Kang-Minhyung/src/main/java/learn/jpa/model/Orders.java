package learn.jpa.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Address address;

    @Builder
    public Orders(Long id, Product product, Member member, Address address) {
        this.id = id;
        this.product = product;
        this.member = member;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", productId=" + product.getId() + ", memberId=" + member.getId() + ", address=" + address + '}';
    }
}
