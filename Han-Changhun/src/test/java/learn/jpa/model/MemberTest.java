package learn.jpa.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberTest {
    @PersistenceUnit
    EntityManagerFactory emf;
    
    EntityManager em;
    EntityTransaction tx;
    
    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }
    
    @AfterEach
    void tearDown() {
        em.close();
        emf.close();
    }
    
    @Test
    void memberTest() throws Exception {
        tx.begin();
        // given
        Member member = Member.builder()
                              .name("홍길동")
                              .age(30)
                              .build();
        
        // when
        em.persist(member);
        em.flush();
        
        Member findMember = em.find(Member.class, member.getId());
        
        
        // then
        assertThat(findMember).isSameAs(member);
        tx.commit();
    }
}
