package learn.jpa.model.ch09;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberEmbedded {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    //AS-IS
    //Embed : 근무기간
//    @Temporal(TemporalType.DATE)
//    LocalDateTime startDate;
//    @Temporal(TemporalType.DATE)
//    LocalDateTime endDate;

    //Embed : 집주소
//    private String city;
//    private String street;
//    private String zipcode;

    //TO-BE
    @Embedded
    Period workPeriod;

    @Embedded
    Address homeAddress;
}
