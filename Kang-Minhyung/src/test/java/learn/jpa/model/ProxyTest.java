package learn.jpa.model;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class ProxyTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    private void init() {
        Team team = Team.builder()
                .name("TeamA")
                .members(new ArrayList<>())
                .build();
        em.persist(team);

        Member member = Member.builder()
                .name("kang")
                .age(30)
                .orders(new ArrayList<>())
                .build();

        Member member2 = Member.builder()
                .name("kang")
                .age(30)
                .orders(new ArrayList<>())
                .build();

        member.mapTeam(team);
        member2.mapTeam(team);

        em.persist(member);
        em.persist(member2);

        em.flush();
        em.clear();
    }

    @Test
    public void proxyTest1() {
        Member member = em.find(Member.class, 1L);
        Team team = member.getTeam();
        team.getName();
    }

    @Test
    public void proxyTest2() {
        Team team = em.find(Team.class, 1L);
    }

    @Test
    public void proxyTest3() {
        Team team = em.find(Team.class, 1L);
        em.flush();
        em.clear();

        team.getMembers().get(0).getId();
    }

    @Test
    public void proxyTest4() {
        Team team = em.find(Team.class, 1L);
        Team team1 = em.getReference(Team.class, 1L);
        team1.getName();
    }

    @Test
    public void proxyTest5() {
        Team team = em.getReference(Team.class, 1L);
        team.getId();
    }

    @Test
    public void proxyTest6() {
        Team team = em.getReference(Team.class, 1L);
        boolean loaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(team);
        System.out.println(loaded);

        Team team2 = em.find(Team.class, 1L);
        boolean loaded2 = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(team2);
        System.out.println(loaded2);
    }

    @Test
    public void proxyTest7() {
        Team team = em.find(Team.class, 1L);
    }

    @Test
    public void proxyTest8() {
        Member member = em.find(Member.class, 1L);
    }

    @Test
    public void proxyTest9() {
        Team team = em.find(Team.class, 1L);
        System.out.println(team);
    }

    @Test
    public void proxyTest10() {
        Member member = em.find(Member.class, 1L);
        member.getTeam().getName();
    }

    @Test
    public void proxyTest11() {
        List<Team> teams = em.createQuery("select t from Team t ", Team.class).getResultList();
        System.out.println(teams);
    }

    @Test
    public void proxyTest12() {
        Team team = em.getReference(Team.class, 1L);
        team.getId();
    }

    @Test
    public void proxyTest13() {
        Member member = em.find(Member.class, 1L);
        Team team = em.getReference(Team.class, 1L);
        member.setTeam(team);
    }
}
