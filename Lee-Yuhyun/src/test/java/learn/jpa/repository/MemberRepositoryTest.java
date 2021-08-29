package learn.jpa.repository;

import learn.jpa.model.ch10.Member;
import learn.jpa.model.ch10.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        Team team1 = Team.builder().name("team1").build();
        Team copyTeam1 = Team.builder().name("team2").build();
        Member member1 = Member.builder()
                .username("name1")
                .age(20)
                .team(team1).build();
        teamRepository.save(team1);
        memberRepository.save(member1);
        Member member2 = Member.builder()
                .username("name2")
                .age(30)
                .team(copyTeam1).build();
        teamRepository.save(copyTeam1);
        memberRepository.save(member2);
    }

    @Test
    @DisplayName("영속성 컨텍스트와 프록시 테스트")
    void persistContextProxyTest() throws Exception {
        Member refMember = memberRepository.getById(2L);
        Member findMember = memberRepository.findById(2L).orElseThrow(Exception::new);
        System.out.println("refMember is proxy: " +  Proxy.isProxyClass(refMember.getClass()));
        System.out.println("proxy: " + refMember);
        System.out.println("entity: " + findMember);
        assertThat(refMember instanceof Member).isTrue();
    }

}