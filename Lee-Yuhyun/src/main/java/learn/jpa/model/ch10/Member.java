package learn.jpa.model.ch10;

import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
//@SqlResultSetMapping(name = "memberWithOrderCount",
//    entities = {@EntityResult(entityClass = Member.class)},
//    columns = {@ColumnResult(name = "ORDER_COUNT")}
//)
@NamedNativeQuery(
        name = "Member.memberSQL",
        query = "SELECT ID, AGE, NAME, TEAM_ID " +
                "FROM MEMBER WHERE AGE > ?",
        resultClass = Member.class
)
@NamedStoredProcedureQuery(
        name = "multiply",
        procedureName = "proc_multiply",
        parameters = {
                @StoredProcedureParameter(name = "inParam", mode =
                ParameterMode.IN, type = Integer.class),
                @StoredProcedureParameter(name = "outParam", mode =
                        ParameterMode.OUT, type = Integer.class)
        }
)
//@ToString(exclude = "team")
@Getter
//@NamedQuery(name = "Member.findByUsername", query="select m from Member m where m.username = :username")
@NamedQueries({
        @NamedQuery(name = "Member.findByUsername", query="select m from Member m where m.username = :username"),
        @NamedQuery(name = "Member.count", query="select count(m) from Member m")
})
public class Member {

    @Builder
    protected Member (String username, int age, Team team) {
        this.username = username;
        this.age = age;
        //상호 참조 코드
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
    }

    protected Member() {
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;

    private int age;

    @ManyToOne//(cascade = {CascadeType.ALL}) TODO: persist child 순서때문에 발생
    private Team team;


    @QueryDelegate(Member.class)
    public static BooleanExpression isOldEnough(QMember member,
                                                int age) {
        return member.age.gt(age);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}