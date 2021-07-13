package learn.jpa.model.ch09;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    public Address() {
    }

    @Builder
    public Address(String city) {
        this.city = city;
    }

    public Address clone() {
        return Address.builder()
                        .city(this.city).build();
    }

    private String city;

    private String street;

    private String zipcode;

    public void setCity(String city) {
        this.city = city;
    }


}
