package learn.jpa.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "MEMBER", uniqueConstraints = {@UniqueConstraint( //추가
name = "NAME_AGE_UNIQUE",
columnNames =  {"NAME", "AGE"})})//
public class Member implements Serializable {
    //receiver가 deserialize할 때 sender가 보낸 클래스와 같은지 검증하기 위해 UID를 사용
    private static final long serialVersionUID = 3990803224604257521L;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private String city;
    private String street;
    private String zipcode;

    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;
}
