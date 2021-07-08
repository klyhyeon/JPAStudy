package learn.jpa.model.ch07;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) //상속관계의 부모클래스에 사용
@DiscriminatorColumn(name = "DTYPE") //자식 테이블 구분자
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id@GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private int price;
}
