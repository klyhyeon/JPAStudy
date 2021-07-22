package learn.jpa.model.ch10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@DataJpaTest
class MemberTest {

//    @Aikutowired
//    TestEntityManager testEm;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void setUp() {
        Member member1 = Member.builder()
                                .username("name1")
                                .age(20).build();
        Member member2 = Member.builder()
                .username("name2")
                .age(30).build();
        em.persist(member1);
        em.persist(member2);
    }

    @Test
    @DisplayName("JPQL 테스트")
    void jqplTest() {
        String jqql = "select m from Member as m where m.username = 'kim'";
        List<Member> getNameKim = em.createQuery(jqql, Member.class).getResultList();
    }

    @Test
    @DisplayName("JPQL 쿼리객체 테스트")
    void jqplTypedQueryTest() {
        //TypedQuery
        TypedQuery<Member> typedQuery =
                em.createQuery("SELECT m FROM Member m", Member.class);

        List<Member> typedQueryResultList = typedQuery.getResultList();
        for (Member member : typedQueryResultList) {
            System.out.println("member = " + member);
        }
        //Query
        Query query =
                em.createQuery("SELECT m.username, m.age FROM Member m");
        List queryResultList = query.getResultList();
        for (Object o : queryResultList) {
            Object[] result = (Object[]) o;
            System.out.println("username = " + result[0]);
            System.out.println("age = " + result[1]);
        }
    }

    @Test
    @DisplayName("JPQL 바인딩 테스트")
    void parameterBindingTest() {
        String usernameParam1 = "name1";
        Member member1 =
                em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class)
                        .setParameter("username", usernameParam1)
                        .getSingleResult();
        System.out.println(member1);
        //위치 기준 파라미터
        String usernameParam2 = "name2";
        Member member2 =
                em.createQuery("SELECT m FROM Member m where m.username = ?1", Member.class)
                    .setParameter(1, usernameParam2)
                    .getSingleResult();
        System.out.println(member2);
    }





}