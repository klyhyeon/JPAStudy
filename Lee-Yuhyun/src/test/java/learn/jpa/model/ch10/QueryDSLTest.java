package learn.jpa.model.ch10;

import com.querydsl.jpa.impl.JPAQueryFactory;
import learn.jpa.config.JpaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

@DataJpaTest
@Import(JpaConfig.class)
public class QueryDSLTest {

    private TestEntityManager testEntityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    public QueryDSLTest(TestEntityManager testEntityManager, JPAQueryFactory jpaQueryFactory) {
        this.testEntityManager = testEntityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @BeforeEach
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void setUp() {
        Team team1 = Team.builder().name("team1").build();
        Team copyTeam1 = Team.builder().name("team1").build();
        Member member1 = Member.builder()
                .username("name1")
                .age(20)
                .team(team1).build();
        testEntityManager.persist(team1);
        testEntityManager.persist(member1);
        Member member2 = Member.builder()
                .username("name2")
                .age(30)
                .team(copyTeam1).build();
        testEntityManager.persist(copyTeam1);
        testEntityManager.persist(member2);
    }
    
    @Test
    public void queryDSLTest() {
        QMember qMember = new QMember("m");
        List<Member> members =
                (List<Member>) jpaQueryFactory.query().from(qMember)
                            .where(qMember.age.gt(20))
                            .fetch();
        System.out.println(members);
    }
}
