package learn.jpa;

import learn.jpa.model.Member;
import learn.jpa.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class InitData {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    static class InitService {
        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
            Team team = Team.builder()
                .name("Team1")
                .build();

            em.persist(team);

            Member member = Member.builder()
                .age(30)
                .name("Kang")
                .build();

            team.setMembers(Collections.singletonList(member));
            em.persist(member);
        }
    }
}
