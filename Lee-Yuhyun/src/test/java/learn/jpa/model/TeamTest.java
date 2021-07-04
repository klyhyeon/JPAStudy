//package learn.jpa.model;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityTransaction;
//import javax.persistence.PersistenceContext;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class TeamTest {
//
//    @PersistenceContext
//    EntityManager em;
//
//    EntityTransaction transaction;
//
//    @Test
//    public void testSave() {
//        Member member1 = Member.builder()
//                .name("member1").build();
//
//        Member member2 = Member.builder()
//                .name("member2").build();
//
//        Team team1 = Team.builder()
//                .name("team1").build();
//        team1.getMembers().add(member1);
//        team1.getMembers().add(member2);
//
//        em.persist(member1);
//        em.persist(member2);
//        em.persist(team1);
//
//        transaction.commit();
//    }
//}