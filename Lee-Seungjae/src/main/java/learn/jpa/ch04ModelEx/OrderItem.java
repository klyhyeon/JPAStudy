package learn.jpa.ch04ModelEx;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "ORDER_ID")
    private Long orderId;

    private int orderPrice;
    private int count;
}
