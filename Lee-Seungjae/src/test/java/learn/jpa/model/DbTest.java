package learn.jpa.model;

import learn.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles("test")
public class DbTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Sql("classpath:member.sql")
    void dbTest() throws Exception {
        Page<Member> members = memberRepository.findByName("테스트", PageRequest.of(0, 3, Sort.by(Order.desc("age"))));

        Pageable pageable = members.getPageable();

        System.out.println("pageable = " + pageable);
        System.out.println("pageable.getSort() = " + pageable.getSort());
        members.getContent().forEach(System.out::println);
    }
}
