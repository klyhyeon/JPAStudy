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
public class Child {

//    @Id
//    @EmbeddedId
//    private ChildId id;
    @Id @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

//    @JoinColumns({
//            @JoinColumn(name = "PARENT_ID1"),
//            @JoinColumn(name = "PARENT_ID2")
//    })
    //EmbeddedId 방식
//    @MapsId("parentId")
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;
}
