package learn.jpa.repository;

import learn.jpa.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByName(String name);

    Member getByName(String name);

    Member readByName(String name);

    Member queryByName(String name);

    Member searchByName(String name);

    Member streamByName(String name);

    Member findFirst1ByName(String name);

    Member findTop1ByName(String name);

    List<Member> findTop2ByName(String name);

    Member findByNameAndAge(String name, int age);

    List<Member> findByNameOrAge(String name, int age);
}
