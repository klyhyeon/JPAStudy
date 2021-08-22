package learn.jpa.repository;

import learn.jpa.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.age > ?1")
    Optional<Member> findByAge(int age);

    List<Member> findByAge(int age, Pageable pageable);
}
