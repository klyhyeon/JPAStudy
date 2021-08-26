package learn.jpa.model.ch14;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@EntityListeners(DuckListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Duck {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String name;

//    @PrePersist
//    public void prePersist() {
//        System.out.println("Duck's prePersist id: " + id);
//    }
//
//    @PostPersist
//    public void postPersist() {
//        System.out.println("Duck's postPersist id: " + id);
//    }
}
