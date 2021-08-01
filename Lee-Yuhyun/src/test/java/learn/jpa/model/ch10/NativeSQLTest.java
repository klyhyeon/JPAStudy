package learn.jpa.model.ch10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@DataJpaTest
public class NativeSQLTest {

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void setUp() {
        Team team1 = Team.builder().name("team1").build();
        Team copyTeam1 = Team.builder().name("team1").build();
        Member member1 = Member.builder()
                .username("name1")
                .age(20)
                .team(team1).build();
        entityManager.persist(team1);
        entityManager.persist(member1);
        Member member2 = Member.builder()
                .username("name2")
                .age(30)
                .team(copyTeam1).build();
        entityManager.persist(copyTeam1);
        entityManager.persist(member2);
        entityManager.persist(Orders.builder().member(member1).build());
        entityManager.persist(Orders.builder().member(member2).build());
    }

    @Test
    @DisplayName("엔티티 조회 테스트")
    public void selectEntityTest() {
        //SQL 정의
        String sql =
                "SELECT ID, AGE, NAME, TEAM_ID " +
                        "FROM MEMBER WHERE AGE > ?";
        Query nativeQuery = entityManager.createNativeQuery(sql, Member.class)
                .setParameter(1, 21);

        List<Member> members = nativeQuery.getResultList();
        System.out.println(members);
    }

    @Test
    @DisplayName("값 조회 테스트")
    public void selectValueTest() {
        String sql =
                "SELECT ID, AGE, NAME, TEAM_ID " +
                        "FROM MEMBER WHERE AGE < ?";
        Query nativeQuery = entityManager.createNativeQuery(sql)
                .setParameter(1, 30);
        System.out.println(nativeQuery.getResultList());
    }

    @Test
    @DisplayName("결과 매핑 테스트")
    public void resultMappingTest() {
        String sql =
                "SELECT M.ID, AGE, NAME, TEAM_ID, I.ORDER_COUNT " + //I.ORDER_COUNT를 조인해서 만들어냄
                "FROM MEMBER M " +
                "LEFT JOIN " + //조인할 테이블을 서브쿼리로 생성함
                "   (SELECT IM.ID, COUNT(*) AS ORDER_COUNT " +
                "   FROM ORDERS O, MEMBER IM " +
                "   WHERE O.MEMBER_ID = IM.ID " +
                "   GROUP BY IM.ID) I " +
                "ON M.ID = I.ID";
        Query nativeQuery = entityManager.createNativeQuery(sql, "memberWithOrderCount");
        List<Object[]> resultList = nativeQuery.getResultList();
        for (Object[] row : resultList) {
            System.out.println("member: " + (Member)row[0]);
            System.out.println("ordercount: " + (BigInteger)row[1]);
        }
    }

    @Test
    @DisplayName("Named 쿼리 테스트")
    public void namedQueryTest() {
        TypedQuery<Member> nativeQuery = entityManager.createNamedQuery("Member.memberSQL", Member.class)
                    .setParameter(1, 21);
        System.out.println(nativeQuery.getResultList());
    }

    @Test
    @DisplayName("스토어드 프로시저 테스트")
    public void storedProcedureTest() {
        StoredProcedureQuery storedProcedureQuery =
                entityManager.createStoredProcedureQuery("proc_multiply");
        storedProcedureQuery.registerStoredProcedureParameter("inParam", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("outParam", Integer.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("inParam", 100);
        storedProcedureQuery.execute();

        Integer out = (Integer)storedProcedureQuery.getOutputParameterValue(2);
        System.out.println("out: " + out);
    }


}
