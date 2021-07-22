package learn.jpa.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JpqlTest {
    @PersistenceUnit
    EntityManagerFactory emf;
    EntityManager em;
    EntityTransaction tx;

    private Member member;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
        member = new Member("kim");
        em.persist(member);
        em.flush();
    }

    @AfterEach
    void clearAll() {
        tx.commit();
        em.close();
        emf.close();
    }

    @Test
    @DisplayName("JPQL 테스트")
    void jpqlTest() {
        String jpql = "select m from Member as m where m.name = 'kim'";
        List<Member> resultList = em.createQuery(jpql, Member.class).getResultList();

        assertThat(resultList.get(0).getName()).isEqualTo("kim");
    }

    @Test
    @DisplayName("criteria 테스트")
    void criteriaTest() {
        //Criteria 사용 준비
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        //루트 클래스(조회를 시작할 클래스)
        Root<Member> m = query.from(Member.class);

        //쿼리 생성
        CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("name"), "kim"));
        List<Member> resultList = em.createQuery(cq).getResultList();

        assertThat(resultList.get(0).getName()).isEqualTo("kim");
    }

}
