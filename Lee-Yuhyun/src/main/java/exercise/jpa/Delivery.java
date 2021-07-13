package exercise.jpa;

import learn.jpa.model.ch09.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "delivery")
    private Order order;

//    private String city;
//
//    private String street;
//
//    private String zipcode;

    @Enumerated(EnumType.STRING)
    private Deliverystatus status;
}
