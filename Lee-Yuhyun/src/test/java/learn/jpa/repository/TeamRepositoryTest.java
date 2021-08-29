package learn.jpa.repository;

import learn.jpa.model.ch10.Member;
import learn.jpa.model.ch10.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

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
    @DisplayName("엔티티 그래프 테스트")
    void entityGraphTest() {
        //given
        String name = "team1";
        ///then
        assertThat(teamRepository.findByName(name).getId()).isEqualTo(1L);
    }

}