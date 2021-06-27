package learn.jpa.ch04ModelEx;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;       //이름
    private int price;         //가격
    private int stockQuantity; //재고 수량

}
