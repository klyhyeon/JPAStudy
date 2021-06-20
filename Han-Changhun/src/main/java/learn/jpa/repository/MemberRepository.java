package learn.jpa.repository;

import learn.jpa.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByName(String name);

    Member getByName(String name);

    Member readByName(String name);

    Member queryByName(String name);

    Member searchByName(String name);

    Member streamByName(String name);
}
