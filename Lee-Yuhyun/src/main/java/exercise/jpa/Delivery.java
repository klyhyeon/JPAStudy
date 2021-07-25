package exercise.jpa;

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

    public Address getAddress() {
        return address;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
