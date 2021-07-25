package learn.jpa.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.*;
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
        Address address = Address.builder()
                .city("city")
                .street("street")
                .zipcode("zipcode")
                .build();

        Address comAddress = Address.builder()
                .city("cocity")
                .street("costreet")
                .zipcode("cozipcode")
                .build();

        Period period = Period.of("20210714", "20210714");

        Member member = Member.builder()
                .name("kim")
                .age(26)
                .period(period)
                .homeAddress(address)
                .companyAddress(comAddress)
                .build();

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

    @Test
    @DisplayName("TypeQuery 테스트")
    void typeQueryTest() {
        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);

        List<Member> resultList = query.getResultList();

        assertThat(resultList.get(0).getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("이름기준 파라미터")
    void nameOfParameterTest() {
        String name = "kim";

        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m where m.name = :name", Member.class);

        query.setParameter("name", name);
        List<Member> resultList = query.getResultList();

        System.out.println(resultList.get(0).getName());

        assertThat(resultList.get(0).getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("여러값 조회")
    void specificColumnTest() {
        List<Object[]> resultList = em.createQuery("SELECT m.homeAddress, m.companyAddress FROM Member m").getResultList();

        for(Object[] obj : resultList) {
            Address homeAddress = (Address) obj[0];
            Address companyAddress = (Address) obj[1];
        }
    }

    @Test
    @DisplayName("페이징 API")
    void pagingApiTest() {
        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m order by m.name desc", Member.class);

        query.setFirstResult(10);
        query.setMaxResults(20);
        query.getResultList();
    }

    @Test
    @DisplayName("컬렉션 테스트")
    void collectionTest() {
        String jpql = "select m from Member m where m.homeAddress is not Empty";
        List<Member> members = em.createQuery(jpql, Member.class).getResultList();
    }

    @Test
    void namedQueryTest() {
        List<Member> members = em.createNamedQuery("Member.findByName", Member.class)
                .setParameter("name", "kim").getResultList();
    }
}
