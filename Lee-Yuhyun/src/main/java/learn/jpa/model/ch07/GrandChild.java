package learn.jpa.model.ch07;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//손자
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrandChild {

//    @EmbeddedId
//    private GrandChildId id;
    @Id @GeneratedValue
    @Column(name = "GRANDCHILD_ID")
    private Long id;


//    @MapsId("childId") //GrandChildId.childId 매핑
//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "PARENT_ID"),
//            @JoinColumn(name = "CHILD_ID")
//    })
//    private Child child;
    @ManyToOne
    @JoinColumn(name = "CHILD_ID")
    private Child child;

    private String name;
}
