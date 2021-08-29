package learn.jpa.repository;

import learn.jpa.model.ch10.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @EntityGraph(value = "Team.members")
    Team findByName(String name);
}
