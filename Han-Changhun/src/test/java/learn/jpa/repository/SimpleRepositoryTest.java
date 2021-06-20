package learn.jpa.repository;

import learn.jpa.model.Member;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import static org.springframework.data.domain.ExampleMatcher.matching;

@DataJpaTest
class SimpleRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("Member_1번을_조회한다")
    void findById() {
        Member member = memberRepository.findById(1L)
                                        .orElseThrow(NoSuchElementException::new);

        assertThat(member.getName()).isEqualTo("siro");
        assertThat(member.getAge()).isEqualTo(29);
    }

    @Test
    @DisplayName("Member_1번_3번을_조회한다")
    void findAllById() {
        List<Member> members = memberRepository.findAllById(Lists.newArrayList(1L, 3L));

        assertThat(members).extracting("name", "age")
                           .contains(tuple("siro", 29),
                                     tuple("dennis", 25))
                           .size().isEqualTo(2);
    }

    @Test
    @DisplayName("Member_초기_데이터는_5명이다")
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
    @DisplayName("Member_1번을_제거한다")
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
    @DisplayName("Member_1번_3번을_제거한다")
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
    @DisplayName("Member_전체제거한다")
    void deleteAll() {
        memberRepository.deleteAll();

        List<Member> members = memberRepository.findAll();

        assertThat(members).isEmpty();
    }

    @Test
    @DisplayName("Member_배치_1번_3번을_제거한다")
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
    @DisplayName("Member_배치_전체제거한다")
    void deleteAllInBatch() {
        memberRepository.deleteAllInBatch();

        List<Member> members = memberRepository.findAll();

        assertThat(members).isEmpty();
    }

    @Test
    @DisplayName("Member_1번이_존재하는지_확인한다")
    void existsById() {
        boolean exists = memberRepository.existsById(1L);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Member_전체수를_조회한다")
    void count() {
        long count = memberRepository.count();
        assertThat(count).isEqualTo(5);
    }

    /**
     * JPA Page는 0부터 시작한다 <br/>
     * <br/>
     * Creates a new unsorted {@link PageRequest}. <br/>
     * page zero-based page index, must not be negative. <br/>
     * the size of the page to be returned, must be greater than 0. <br/>
     */
    @Test
    @DisplayName("Page_API_학습테스트한다")
    void page() {
        Page<Member> members = memberRepository.findAll(PageRequest.of(1, 3));
        Pageable pageable = members.getPageable();

        Sort sort = members.getSort();
        int pageNumber = pageable.getPageNumber();
        int totalPages = members.getTotalPages();
        long totalElements = members.getTotalElements();
        int numberOfElements = members.getNumberOfElements();
        int size = members.getSize();

        assertThat(sort.isUnsorted()).isTrue();
        assertThat(pageNumber).isEqualTo(1);
        assertThat(totalPages).isEqualTo(2);
        assertThat(totalElements).isEqualTo(5);
        assertThat(numberOfElements).isEqualTo(2);
        assertThat(size).isEqualTo(3);
        assertThat(members).extracting("name", "age")
                           .contains(tuple("james", 41),
                                     tuple("michael", 33))
                           .size().isEqualTo(2);
    }

    @Test
    @DisplayName("Example_API_사용하여_조건검색을_학습테스트한다")
    void exampleFindAll() {
        ExampleMatcher matcher = matching()
                .withIgnorePaths("age") // age 는 무시하고 검색한다
                .withMatcher("name", GenericPropertyMatchers.contains()); // name 을 검색조건에 포함시킨다 - like 검색

        // 조건 검색을 위한 Member proxy 를 생성한다
        // name 에 i가 들어가는 멤버를 조회한다
        // age 는 무시되므로 값이 몇이든 의미없다
        Example<Member> example = Example.of(Member.createMember("i", 0), matcher);

        List<Member> members = memberRepository.findAll(example);

        assertThat(members).extracting("name", "age")
                           .contains(tuple("siro", 29),
                                     tuple("sophia", 32),
                                     tuple("dennis", 25),
                                     tuple("michael", 33))
                           .size().isEqualTo(4);
    }

    /**
     * 참고자료 경로
     *
     * @see "Han-Changhun/src/test/resources/query-method-1.png"
     */
    @Test
    @DisplayName("Query_Methods_실습_읽기_접두사")
    void queryMethodsV1() {
        Member member = Member.createMember("tester", 77);
        Member tester = memberRepository.saveAndFlush(member);

        assertThat(tester).usingRecursiveComparison().isEqualTo(memberRepository.findByName("tester"));
        assertThat(tester).usingRecursiveComparison().isEqualTo(memberRepository.getByName("tester"));
        assertThat(tester).usingRecursiveComparison().isEqualTo(memberRepository.readByName("tester"));
        assertThat(tester).usingRecursiveComparison().isEqualTo(memberRepository.queryByName("tester"));
        assertThat(tester).usingRecursiveComparison().isEqualTo(memberRepository.searchByName("tester"));
        assertThat(tester).usingRecursiveComparison().isEqualTo(memberRepository.streamByName("tester"));
    }

    /**
     * 참고자료 경로
     *
     * @see "Han-Changhun/src/test/resources/query-method-1.png"
     */
    @Test
    @DisplayName("Query_Methods_TOP_조회")
    void queryMethodsV2() {
        // id=1 siro 와 id=6 siro 가 존재하는 상황에서
        // limit query 를 사용하여 id 우선순위가 더 높은 데이터를 조회한다
        Member member = Member.createMember("siro", 77);
        memberRepository.saveAndFlush(member); // id=6 siro save

        Member siro = memberRepository.findById(1L).get(); // id=1 siro select

        assertThat(siro).usingRecursiveComparison().isEqualTo(memberRepository.findTop1ByName("siro"));
        assertThat(siro).usingRecursiveComparison().isEqualTo(memberRepository.findFirst1ByName("siro"));

        List<Member> members = memberRepository.findTop2ByName("siro"); //  limit = 2 select

        assertThat(members).extracting("name", "age")
                           .contains(tuple("siro", 29),
                                     tuple("siro", 77))
                           .size().isEqualTo(2);
    }

    /**
     * 참고자료 경로
     *
     * @see "Han-Changhun/src/test/resources/query-method-1.png"
     */
    @Test
    @DisplayName("Query_Methods_AND_조회")
    void queryMethodsV3() {
        Member member = Member.createMember("siro", 77);
        memberRepository.saveAndFlush(member); // id=6 siro save

        Member siro = memberRepository.findByNameAndAge("siro", 77);

        assertThat(siro).extracting("name", "age")
                        .containsExactly("siro", 77);
    }

    /**
     * 참고자료 경로
     *
     * @see "Han-Changhun/src/test/resources/query-method-1.png"
     */
    @Test
    @DisplayName("Query_Methods_OR_조회")
    void queryMethodsV4() {
        Member member = Member.createMember("siro", 25);
        memberRepository.saveAndFlush(member); // id=6 siro save

        List<Member> members = memberRepository.findByNameOrAge("siro", 25);

        assertThat(members).extracting("name", "age")
                           .contains(tuple("siro", 29),
                                     tuple("dennis", 25),
                                     tuple("siro", 25))
                           .size().isEqualTo(3);
    }
}
