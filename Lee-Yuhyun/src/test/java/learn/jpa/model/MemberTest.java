package learn.jpa.model;

import learn.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class MemberTest {
    @Autowired
    MemberRepository memberRepository;

    EntityManagerFactory emf;
    EntityManager em;
    EntityTransaction transaction;

    @Test
    void memberTest() throws Exception {

        Member member = Member.builder()
//                                .username("유현")
//                                .age(30)
                                .build();

        Member newMember = memberRepository.save(member);

//        assertThat(newMember.getAge()).isEqualTo(39);


//        // given
//        Member member = Member.builder()
//                              .username("홍길동")
//                              .age(30)
//                              .build();
//
//        // when
//        Member save = repository.save(member);
//
//        // then
//        assertThat(save).isSameAs(member);
    }

    //team까지 조회할 필요가 없습니다.
    @Test
    public void printUser(String memberId) {
        Member member = em.find(Member.class, memberId);
        Team team = member.getTeam();
        System.out.println("회원이름: " + member.getName());
//        System.out.println("소속팀 : " + team.getName());

    }
}
