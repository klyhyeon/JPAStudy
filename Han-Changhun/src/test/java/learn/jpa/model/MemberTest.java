package learn.jpa.model;

import learn.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberTest {
    @Autowired
    MemberRepository repository;
    
    @Test
    void memberTest() throws Exception {
        // given
        Member member = Member.builder()
                              .name("홍길동")
                              .age(30)
                              .build();
        
        // when
        Member save = repository.save(member);
        
        // then
        assertThat(save).isSameAs(member);
    }
}
