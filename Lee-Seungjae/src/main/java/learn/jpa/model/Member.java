package learn.jpa.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Serializable {
    private static final long serialVersionUID = 3990803224604257521L;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    @Embedded
    private Period period;

    @Embedded
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "COMPANY_ZIPCODE")),
    })
    private Address companyAddress;

    @Builder
    public Member(Long id, String name, int age, Period period, Address homeAddress, Address companyAddress) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.period = period;
        this.homeAddress = homeAddress;
        this.companyAddress = companyAddress;
    }
}
