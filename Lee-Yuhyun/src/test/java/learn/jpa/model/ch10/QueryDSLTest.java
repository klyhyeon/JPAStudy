package learn.jpa.model.ch10;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.querydsl.sql.SQLExpressions;
import learn.jpa.config.JpaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static learn.jpa.model.ch10.QMember.member;
import static learn.jpa.model.ch10.QTeam.team;

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
        testEntityManager.persist(team1);
        testEntityManager.persist(member1);
        Member member2 = Member.builder()
                .username("name2")
                .age(30)
                .team(copyTeam1).build();
        testEntityManager.persist(copyTeam1);
        testEntityManager.persist(member2);

        testEntityManager.flush();
        testEntityManager.clear();
    }
    
    @Test
    @DisplayName("Initalize QueryDSL 테스트")
    public void queryDSLTest() {
        List<Member> members =
                (List<Member>) jpaQueryFactory.query().from(member)
                            .where(member.age.gt(20))
                            .orderBy(member.username.desc())
                            .fetch();
        System.out.println(members);
    }

    @Test
    @DisplayName("검색조건 쿼리 테스트")
    public void searchQueryTest() {
        List<Member> members =
                (List<Member>) jpaQueryFactory.query().from(member)
//                        .where(member.username.contains("nam"))
//                        .where(member.username.startsWith("n"))
                        .where(member.username.eq("name1"))
                        .orderBy(member.username.desc())
                        .fetch();
        System.out.println(members);
    }

    @Test
    @DisplayName("페이징과 정렬 테스트")
    public void pagingAndListingTest() {
        QueryResults<Member> members =
                (QueryResults<Member>) jpaQueryFactory.query()
                        .from(member)
                        .orderBy(member.username.desc(), member.age.asc())
                        .offset(1).limit(2)
                        .fetchResults();
        System.out.println("total: "+members.getTotal());
        System.out.println("offset: "+members.getOffset());
        System.out.println("limit: "+members.getLimit());
    }

    @Test
    @DisplayName("그룹 테스트")
    public void groupTest() {
        List<Member> members =
                (List<Member>) jpaQueryFactory.query()
                        .from(member)
                        .orderBy(member.username.desc())
                        .groupBy(member.username).having(member.age.eq(30))
                        .fetch();
    }

    @Test
    @DisplayName("조인 테스트")
    public void joinTest() {
        List teams = jpaQueryFactory.from(member)
                .leftJoin(member.team, team)
                .fetch();
    }

    @Test
    @DisplayName("서브쿼리 테스트")
    public void subqueryTest() {
        System.out.println(jpaQueryFactory
                .select(member)
                .from(member)
                .where(member.age.eq(
                    SQLExpressions
                            .select(member.age.max())
                            .from(member)))
                .fetch());
    }

    @Test
    @DisplayName("프로젝션 테스트")
    public void projectionTest() {
        QMember qMember = member;
        List<UserDTO> result =
                jpaQueryFactory
                    .select(
                            Projections.constructor(UserDTO.class,
                                    member.username, member.age)

                    )
                    .from(member)
                    .fetch();
        System.out.println(result.get(0));
    }

    @Test
    @DisplayName("수정,삭제,배치쿼리 테스트")
    public void batchQueryTest() {
        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, member);
        Long count = updateClause.where(member.username.contains("1"))
                .set(member.age, member.age.add(1))
                .execute();
        System.out.println(count);
        //TODO: 왜 update된 member age가 그대로인지
        List<Member> members =
                (List<Member>) jpaQueryFactory
                    .from(member)
                    .where(member.username.contains("1"))
                    .fetch();
        System.out.println("member age: " + members.get(0).getAge());
    }

    @Test
    @DisplayName("동적 쿼리")
    public void dynamicQuery() {
        Map<String,Object> param = new HashMap<>();
        param.put("name", "name2");
        param.put("age", 30);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(param.get("name").toString()))
            builder.and(member.username.contains(param.get("name").toString()));
        if (param.get("age") != null)
            builder.and(member.age.gt((Integer)param.get("age")));
        List<Member> result =
                (List<Member>) jpaQueryFactory.from(member)
                .where(builder)
                .fetch();
        System.out.println(result);
    }

    @Test
    @DisplayName("메소드 위임 테스트")
    public void delegateMethodTest() {
        jpaQueryFactory
                .from(member)
                .where(member.isOldEnough(20))
                .fetch();
    }
}
