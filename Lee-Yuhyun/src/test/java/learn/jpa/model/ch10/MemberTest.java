package learn.jpa.model.ch10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@DataJpaTest
class MemberTest {

//    @Autowired
//    TestEntityManager testEm;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("JPQL 테스트")
    void jqplTest() {
        String jqql = "select m from Member as m where m.username = 'kim'";
        List<Member> getNameKim = em.createQuery(jqql, Member.class).getResultList();
    }


}