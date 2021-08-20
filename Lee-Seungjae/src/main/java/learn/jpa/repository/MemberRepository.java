package learn.jpa.repository;

import java.util.List;
import learn.jpa.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByName(String name);
    Page<Member> findMembersByName(String name, Pageable pageable);
    Page<Member> findByName(String name, Pageable pageable);
    Page<Member> findByNameOrderByAgeDesc(String name, Pageable pageable);
}
