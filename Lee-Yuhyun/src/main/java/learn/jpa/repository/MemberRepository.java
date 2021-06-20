package learn.jpa.repository;

import learn.jpa.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    Member save(Member member);

    @Override
    Optional<Member> findById(Long id);

    @Override
    List<Member> findAll();

    @Override
    void deleteById(Long id);
}
