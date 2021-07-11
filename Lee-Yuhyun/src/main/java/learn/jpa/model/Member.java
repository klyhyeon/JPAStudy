package learn.jpa.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "MEMBER")//
public class Member implements Serializable {
    //receiver가 deserialize할 때 sender가 보낸 클래스와 같은지 검증하기 위해 UID를 사용
    private static final long serialVersionUID = 3990803224604257521L;
    
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private String id;
    private String name;

    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public void setTeam(Team team) {
        this.team = team;

        //TODO: 하위 코드 해석하기
        //무한루프에 빠지지 않도록 체크
        if (!team.getMembers().contains(this)) {
            team.getMembers().add(this);
        }
    }

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT",
            //조인할 컬럼정보 지정
            joinColumns = @JoinColumn(name = "MEMBER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private List<Product> products = new ArrayList<Product>();

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<MemberProduct>();

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();
}
