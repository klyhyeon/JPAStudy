package learn.jpa.model;

import learn.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class MemberTest {
    @Autowired
    MemberRepository memberRepository;
    
    @Test
    void memberTest() throws Exception {

        Member member = Member.builder()
                                .username("유현")
                                .age(30)
                                .build();

        Member newMember = memberRepository.save(member);

        assertThat(newMember.getAge()).isEqualTo(39);


//        // given
//        Member member = Member.builder()
//                              .username("홍길동")
//                              .age(30)
//                              .build();
//
//        // when
//        Member save = repository.save(member);
//
//        // then
//        assertThat(save).isSameAs(member);
    }
}
