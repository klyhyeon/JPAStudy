package learn.jpa.model;

import learn.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

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

    @Test
    void generateIdTest1() {
        Member member = Member.builder()
                .id(10L)
                .name("홍길동")
                .age(30)
                .build();

        Member save = repository.save(member);

        assertThat(save.getId()).isEqualTo(member.getId());
    }

    @Test
    void generateIdTest2() {
        Member member = Member.builder()
                .id(10L)
                .name("홍길동")
                .age(30)
                .build();

        Member save = repository.save(member);

        assertThat(save.getId()).isNotEqualTo(member.getId());
    }

    @Test
    void generateIdTest3() {
        Member member = Member.builder()
                .id(10L)
                .name("홍길동")
                .age(30)
                .build();

        Member save = repository.save(member);

        assertThat(save.getId()).isEqualTo(17);
    }

    @Test
    void generateIdTest4() {
        Member member = Member.builder()
                .id(10L)
                .name("홍길동")
                .age(30)
                .build();

        Member save = repository.save(member);

        Member member2 = Member.builder()
                .id(10L)
                .name("홍길동")
                .age(30)
                .build();

        Member save2 = repository.save(member2);
        System.out.println(save.getId());
        System.out.println(save2.getId());

        assertThat(save.getId()).isEqualTo(3);
    }

    @Test
    public void test01() {
        //given
        Pageable pageable = Pageable.ofSize(1).withPage(2);
        repository.findByAge(10, pageable);

        //when


        //then
    }
}
