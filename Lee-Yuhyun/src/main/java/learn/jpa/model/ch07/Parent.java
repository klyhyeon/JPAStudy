package learn.jpa.model.ch07;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parent {

    //IdClass 방식
//    @Id
//    @Column(name = "PARENT_ID1")
//    private String id1;
//
//    @Id
//    @Column(name = "PARENT_ID2")
//    private String id2;

    //Embedded 방식
//    @EmbeddedId
//    private ParentId id;
    @Id @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;
    private String name;
}
