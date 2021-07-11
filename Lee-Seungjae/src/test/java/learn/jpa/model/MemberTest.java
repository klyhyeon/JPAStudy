package learn.jpa.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberTest {

    @PersistenceUnit //J2EE환경에서는 이렇게 팩토리를 얻을 수 있다.
    EntityManagerFactory emf;
    // 책에 나온 Persistence.createEntityManagerFactory("");

    EntityManager em;
    EntityTransaction tx;

    @BeforeEach
    void connectJPA(){
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }
    @AfterEach
    void closeAll(){ //JPA 반드시 종료 무조건 시켜주자
        em.close();
        emf.close();
    }

    @Test
    @DisplayName("2장 테스트")
    void memberTest(){
        //given
        tx.begin();

        //when
        Member member = Member.builder()
                              .name("홍길동")
                              .age(30)
                              .build();

        //등록
        em.persist(member);

        em.flush();

        Member insertData = em.find(Member.class, member.getId());
        
        // then
        assertThat(insertData.getName()).isEqualTo(member.getName());
        assertThat(insertData.getAge()).isEqualTo(member.getAge());
        tx.commit();
    }


    @Test
    @DisplayName("프록시 테스트")
    void proxyTest(){
        tx.begin();
        Team team = Team.builder()
                        .name("team1")
                        .build();


        Member member = Member.builder()
                .name("홍길동")
                .age(27)
                .team(team)
                .build();

        em.persist(team);
        em.persist(member);

        Member resultMember = em.find(Member.class, member.getId());
        Team resultTeam = resultMember.getTeam();
        em.flush();

        assertThat(resultMember.getName()).isEqualTo("홍길동");
        assertThat(resultMember.getAge()).isEqualTo(27);
        assertThat(resultTeam.getName()).isEqualTo("team1");
        tx.commit();
    }
}
