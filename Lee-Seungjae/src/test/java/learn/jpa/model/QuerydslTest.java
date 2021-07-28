package learn.jpa.model;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class QuerydslTest {
    @PersistenceUnit
    EntityManagerFactory emf;
    EntityManager em;
    EntityTransaction tx;
    JPAQueryFactory jpaQueryFactory;

    private Member member;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        jpaQueryFactory = new JPAQueryFactory(em);
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

        member = Member.builder()
                .name("kim")
                .age(26)
                .period(period)
                .homeAddress(address)
                .companyAddress(comAddress)
                .build();

        em.persist(member);
        em.flush();
    }

    @Test
    @DisplayName("QueryDSL 시작")
    void querydslTest() {
        QMember qMember = new QMember("m"); //생성된 별칭
        List<Member> members = jpaQueryFactory.selectFrom(qMember)
                .where(qMember.name.eq("kim")).orderBy(qMember.id.desc()).fetch(); //책에서 다른 부분

        assertThat(members.get(0).getName()).isEqualTo("kim");
        assertThat(members.get(0).getAge()).isEqualTo(26);
    }
}
