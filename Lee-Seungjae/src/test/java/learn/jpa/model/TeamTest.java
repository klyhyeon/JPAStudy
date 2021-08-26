package learn.jpa.model;

import java.util.List;
import learn.jpa.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TeamTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    void 컬렉션_테스트() {
        Team team = new Team(true);
        teamRepository.save(team);
        List<Team> list = teamRepository.findAll();
        System.out.println(list.get(0).toString());
    }

}