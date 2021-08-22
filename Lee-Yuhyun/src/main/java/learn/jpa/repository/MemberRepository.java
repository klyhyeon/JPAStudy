package learn.jpa.repository;

import learn.jpa.model.ch10.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
