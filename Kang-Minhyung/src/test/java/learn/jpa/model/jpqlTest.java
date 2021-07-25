package learn.jpa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@DataJpaTest
public class jpqlTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    public void init() {
        Team team1 = Team.builder().name("team1").build();
        Member member1 = Member.builder().name("name1").age(30).team(team1).build();
        Member member2 = Member.builder().name("name2").age(20).team(team1).build();
        team1.setMembers(List.of(member1, member2));

        em.persist(team1);
        em.persist(member1);
        em.persist(member2);

        Product product = Product.builder().name("과자").price(1000).build();

        em.persist(product);

        Orders orders = Orders.builder().address(Address.builder().city("광명시").street("철산로").zipcode("57").build()).member(member1).product(product).build();
        em.persist(orders);

        em.flush();
        em.clear();
    }


    @Test
    public void test01() {
        TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);

        List<Member> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test02() {
        Query query = em.createQuery("select m.name, m.age from Member m");

        List resultList = query.getResultList();

        for (Object o : resultList) {
            Object[] result = (Object[]) o;
            System.out.println(result[0]);
            System.out.println(result[1]);
        }
    }

    @Test
    public void test03() {
        String nameParam = "name1";
        TypedQuery<Member> query = em.createQuery("select m from Member m where m.name = :name", Member.class);
        query.setParameter("name", nameParam);

        List<Member> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test04() {
        String nameParam = "name1";
        TypedQuery<Member> query = em.createQuery("select m from Member m where m.name = ?1", Member.class);
        query.setParameter(1, nameParam);

        List<Member> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test05() {
        Query query = em.createQuery("select m.name from Member m");

        List resultList = query.getResultList();


        for (Object o : resultList) {
            System.out.println(o);
        }
    }

    @Test
    public void test06() {
        Query query = em.createQuery("select o.member, o.product from Orders o");

        List<Object[]> resultList = query.getResultList();

        for (Object[] o : resultList) {
            System.out.println(o[0]);
            System.out.println(o[1]);
        }
    }

    @Test
    public void test07() {
        TypedQuery<UserDto> query = em.createQuery("select new learn.jpa.model.UserDto(m.name, m.age) from Member m", UserDto.class);

        List<UserDto> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test08() {
        TypedQuery<UserDto> query = em.createQuery("select new learn.jpa.model.UserDto(m.name, m.age) from Member m", UserDto.class);
        query.setFirstResult(1);
        query.setMaxResults(1);

        List<UserDto> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test09() {
        Query query = em.createQuery("select count(m), sum(m.age), avg(m.age) from Member m");

        List<Object[]> resultList = query.getResultList();

        for (Object[] o : resultList) {
            System.out.println("Count : " + o[0]);
            System.out.println("Sum : " + o[1]);
            System.out.println("Avg : " + o[2]);
        }
    }

    @Test
    public void test10() {
        Query query = em.createQuery("select  count(m), sum(m.age), avg(m.age) from Member m left join m.team t group by t.name having avg(m.age) >= 10");

        List<Object[]> resultList = query.getResultList();

        for (Object[] o : resultList) {
            System.out.println("Count : " + o[0]);
            System.out.println("Sum : " + o[1]);
            System.out.println("Avg : " + o[2]);
        }
    }

    @Test
    public void test11() {
        TypedQuery<Member> query = em.createQuery("select m from Member m order by m.age", Member.class);

        List<Member> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test12() {
        String teamName = "team1";
        TypedQuery<Member> query = em.createQuery("select m from Member m inner join m.team t where t.name = :teamName", Member.class);
        query.setParameter("teamName", teamName);

        List<Member> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test13() {
        String teamName = "team1";
        TypedQuery<Member> query = em.createQuery("select m from Member m left join m.team t where t.name = :teamName", Member.class);
        query.setParameter("teamName", teamName);

        List<Member> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test14() {
        TypedQuery<Team> query = em.createQuery("select t from Team t left join t.members m", Team.class);

        List<Team> resultList = query.getResultList();

        System.out.println(resultList.get(0).getMembers().get(0).getName());

//        resultList.forEach(System.out::println);
    }

    @Test
    public void test15() {
        TypedQuery<Member> query = em.createQuery("select m from Member m, Team t where m.name = t.name", Member.class);

        List<Member> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test16() {
        TypedQuery<Member> query = em.createQuery("select m from Member m join fetch m.team", Member.class);

        List<Member> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test17() {
        TypedQuery<Team> query = em.createQuery("select t from Team t join fetch t.members", Team.class);

        List<Team> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test18() {
        TypedQuery<Team> query = em.createQuery("select distinct t from Team t join fetch t.members", Team.class);

        List<Team> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test19() {
        TypedQuery<Member> query = em.createQuery("select m from Member m where m.age > (select avg(m2.age) from Member m2)", Member.class);

        List<Member> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test20() {
        Query query = em.createQuery("select case t.name when 'team1' then '인센티브110%' when 'team2' then '인센티브120%' else '인센티브105%' end from Team t");

        List<Object> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test21() {
        Query query = em.createQuery("select coalesce(m.name, '이름없는 회원') from Member m");

        List<Object> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test22() {
        Query query = em.createQuery("select count(m) from Member m");

        List<Object> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test23() {
        Query query = em.createQuery("select m from Member m where m.team = 1L");

        List<Object> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }

    @Test
    public void test24() {
        TypedQuery<Member> query = em.createNamedQuery("Member.findByName", Member.class);

        query.setParameter("name", "name1");

        List<Member> resultList = query.getResultList();

        resultList.forEach(System.out::println);
    }
}
