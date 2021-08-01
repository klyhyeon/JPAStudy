package learn.jpa.model.ch10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@DataJpaTest
public class AdvancedJPQLTest {

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
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

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("벌크연산 테스트")
    public void bulkOperateTest() {

        String qlString =
                "update Member m " +
                        "set m.age = m.age * 2 " +
                        "where m.username = :name";

        int resultCount = entityManager.createQuery(qlString)
                .setParameter("name", "name1")
                .executeUpdate();
        System.out.println(resultCount);

        entityManager.flush();
        entityManager.clear();

        Member member1 =
                entityManager.createQuery("select m from Member m where m.username = :name",
                        Member.class)
                        .setParameter("name", "name1")
                        .getSingleResult();
        //출력결과 : 40
        System.out.println("member1 수정 후: " + member1.getAge());
    }

    @Test
    @DisplayName("영속성 컨텍스트와 JPQL 테스트")
    public void persistenceContextJPQLTest() {
        entityManager.find(Member.class, 1L);

        List<Member> members =
                entityManager.createQuery("select m from Member m where m.id = :id" ,
                        Member.class)
                        .setParameter("id", 1L)
                        .getResultList();

    }

    @Test
    @DisplayName("플러시와 JPQL 테스트")
    public void flushJPQLTest() {
        //플러시를 COMMIT으로 설정하면 DB에 반영되지 않아 수정된 값을 반환하지 못합니다.
        entityManager.setFlushMode(FlushModeType.COMMIT);

        Team team1 = Team.builder().name("team1").build();
        Member member1 = Member.builder()
                .username("name1")
                .age(20)
                .team(team1).build();
        entityManager.persist(team1);
        entityManager.persist(member1);

        member1.setUsername("flush1");

        List<Member> members =
                entityManager.createQuery("select m from Member m where m.username = :name" ,
                        Member.class)
                        .setParameter("name", "flush1")
                        .getResultList();
        System.out.println(members);
    }
}
