package learn.jpa.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TeamTest {

    @PersistenceUnit
    EntityManagerFactory emf;

    EntityManager em;
    EntityTransaction transaction;

    @BeforeEach
    public void init() {
        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }

    @AfterEach
    public void close() {
        em.close();
        emf.close();
    }

    @Test
    public void testSave() {

        transaction.begin();

        Member member1 = Member.builder()
                .name("member1").build();

        Member member2 = Member.builder()
                .name("member2").build();

        Team team1 = Team.builder()
                .name("team1").build();
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        em.persist(member1);
        em.persist(member2);
        em.persist(team1);

        transaction.commit();
    }
}