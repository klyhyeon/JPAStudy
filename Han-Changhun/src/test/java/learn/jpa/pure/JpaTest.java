package learn.jpa.pure;

import learn.jpa.factories.PersistenceFactory;
import learn.jpa.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JpaTest {
    @Test
    @DisplayName("순수_JPA_테스트_샘플")
    void jpaTest() throws Exception {
        EntityManager em = PersistenceFactory.getEntityManager(); // get Singleton
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // given
        Member member = Member.builder()
                              .name("홍길동")
                              .age(30)
                              .build();

        // when
        try {
            em.persist(member);
            em.flush();

            Member findMember = em.find(Member.class, member.getId());

            // then
            assertThat(findMember).isSameAs(member);
            tx.commit();
        }
        catch(Exception e) {
            tx.rollback();
        }
        finally {
            em.close();
        }
    }
}
