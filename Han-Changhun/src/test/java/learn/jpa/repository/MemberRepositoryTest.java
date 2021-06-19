package learn.jpa.repository;

import learn.jpa.model.Member;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("MEMBER_1번을_조회한다")
    void findById() {
        Member member = memberRepository.findById(1L).orElseThrow(() -> new NoSuchElementException("Member not found"));

        assertThat(member.getName()).isEqualTo("siro");
        assertThat(member.getAge()).isEqualTo(29);
    }

    @Test
    @DisplayName("MEMBER_1번_3번을_조회한다")
    void findAllById() {
        List<Member> members = memberRepository.findAllById(Lists.newArrayList(1L, 3L));

        assertThat(members).extracting("name", "age")
                           .contains(tuple("siro", 29),
                                     tuple("dennis", 25))
                           .size().isEqualTo(2);
    }

    @Test
    @DisplayName("MEMBER_초기_데이터는_5명이다")
    void findAll() {
        List<Member> members = memberRepository.findAll();

        assertThat(members).extracting("name", "age")
                           .contains(tuple("siro", 29),
                                     tuple("sophia", 32),
                                     tuple("dennis", 25),
                                     tuple("james", 41),
                                     tuple("michael", 33))
                           .size().isEqualTo(5);
    }

    @Test
    @DisplayName("MEMBER_1번을_제거한다")
    void deleteById() {
        memberRepository.deleteById(1L);

        List<Member> members = memberRepository.findAll();

        assertThat(members).extracting("name", "age")
                           .contains(tuple("sophia", 32),
                                     tuple("dennis", 25),
                                     tuple("james", 41),
                                     tuple("michael", 33))
                           .size().isEqualTo(4);
    }

    @Test
    @DisplayName("MEMBER_1번_3번을_제거한다")
    void deleteAllById() {
        memberRepository.deleteAllById(Lists.newArrayList(1L, 3L));

        List<Member> members = memberRepository.findAll();

        assertThat(members).extracting("name", "age")
                           .contains(tuple("sophia", 32),
                                     tuple("james", 41),
                                     tuple("michael", 33))
                           .size().isEqualTo(3);
    }

    @Test
    @DisplayName("MEMBER_전체제거한다")
    void deleteAll() {
        memberRepository.deleteAll();

        List<Member> members = memberRepository.findAll();

        assertThat(members).isEmpty();
    }

    @Test
    @DisplayName("MEMBER_배치_1번_3번을_제거한다")
    void deleteAllByIdInBatch() {
        memberRepository.deleteAllByIdInBatch(Lists.newArrayList(1L, 3L));

        List<Member> members = memberRepository.findAll();

        assertThat(members).extracting("name", "age")
                           .contains(tuple("sophia", 32),
                                     tuple("james", 41),
                                     tuple("michael", 33))
                           .size().isEqualTo(3);
    }

    @Test
    @DisplayName("MEMBER_배치_전체제거한다")
    void deleteAllInBatch() {
        memberRepository.deleteAllInBatch();

        List<Member> members = memberRepository.findAll();

        assertThat(members).isEmpty();
    }

    @Test
    @DisplayName("MEMBER_1번이_존재하는지_확인한다")
    void existsById() {
        boolean exists = memberRepository.existsById(1L);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("MEMBER_전체_회원수를_조회한다")
    void count() {
        long count = memberRepository.count();
        assertThat(count).isEqualTo(5);
    }
}
