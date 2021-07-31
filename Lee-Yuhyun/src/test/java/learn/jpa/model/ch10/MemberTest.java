package learn.jpa.model.ch10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@DataJpaTest
class MemberTest {

//    @Autowired
//    TestEntityManager testEm;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void setUp() {
        Team team1 = Team.builder().name("team1").build();
        Team copyTeam1 = Team.builder().name("team1").build();
        Member member1 = Member.builder()
                                .username("name1")
                                .age(20)
                                .team(team1).build();
        em.persist(team1);
        em.persist(member1);
        Member member2 = Member.builder()
                .username("name2")
                .age(30)
                .team(copyTeam1).build();
        em.persist(copyTeam1);
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

    @Test
    @DisplayName("JPQL 프로젝션 테스트")
    void projectionTest() {
        List<Object[]> resultList =
                em.createQuery("SELECT m.username, m.age FROM Member m")
                .getResultList();
        for (Object[] row : resultList) {
            String username = (String) row[0];
            Integer age = (Integer) row[1];
        }

//        List<UserDTO> userDTOs = new ArrayList<>();
//        for (Object[] row : resultList) {
//            UserDTO userDTO = UserDTO.builder()
//                                        .username((String) row[0])
//                                        .age((Integer) row[1]).build();
//            userDTOs.add(userDTO);
//            System.out.println(userDTO);
//        }
        // NEW 명령어 사용
        List<UserDTO> useNewCommandResultList =
                em.createQuery("SELECT new learn.jpa.model.ch10.UserDTO(m.username, m.age)" +
                        "FROM Member m", UserDTO.class).getResultList();
        System.out.println(useNewCommandResultList);
    }

    @Test
    @DisplayName("페이징 API 테스트")
    void pagingAPITest() {
        TypedQuery<Member> query =
                em.createQuery("SELECT m FROM Member m ORDER BY m.username DESC", Member.class);
        query.setFirstResult(10);
        query.setMaxResults(20);
        query.getResultList();
    }

    @Test
    @DisplayName("조인 테스트")
    void joinTest() {
        //내부조인
//        String teamName = "팀A";
//        String query = "SELECT m FROM Member m INNER JOIN m.team t WHERE t.name = :teamName";
//        List<Member> members = em.createQuery(query, Member.class)
//                .setParameter("teamName", teamName)
//                .getResultList();
        //페치조인
        String jpql = "select m from Member m join fetch m.team";
        List<Member> fetchJoinMembers =
                em.createQuery(jpql, Member.class)
                .getResultList();
        for (Member member : fetchJoinMembers) {
            System.out.println("username: " + member.getUsername() + ", teamname: " + member.getTeam().name());
        }

        String collectionJpql = "select t from Team t join fetch t.members where t.name = 'team1'";
        List<Team> fetchJoinTeams =
                em.createQuery(collectionJpql, Team.class)
                .getResultList();
        for (Team t : fetchJoinTeams) {
            System.out.println("teamname: " + t.name() + ", team = " + t);
            for (Member member : t.getMembers()) {
                System.out.println("-> username = " + member.getUsername() + "member = " + member);
            }
        }
    }

    @Test
    @DisplayName("@NamedQuery 테스트")
    void namedQueryTest() {
        List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", "name1")
                .getResultList();
        System.out.println(resultList.get(0).getUsername());
    }





}