package learn.jpa.model;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static learn.jpa.model.QMember.member;
import static learn.jpa.model.QTeam.team;

@DataJpaTest
public class QuerydslTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory query;

    @BeforeEach
    void setUp() {
        query = new JPAQueryFactory(em);

        Team team1 = Team.builder()
            .name("team1")
            .build();
        Member member1 = Member.builder()
            .name("name1")
            .age(30)
            .team(team1)
            .build();
        Member member2 = Member.builder()
            .name("name2")
            .age(20)
            .team(team1)
            .build();
        team1.setMembers(List.of(member1, member2));

        em.persist(team1);
        em.persist(member1);
        em.persist(member2);

        Product product = Product.builder()
            .name("과자")
            .price(1000)
            .build();

        em.persist(product);

        Orders orders = Orders.builder()
            .address(Address.builder()
                .city("광명시")
                .street("철산로")
                .zipcode("57")
                .build())
            .member(member1)
            .product(product)
            .build();
        em.persist(orders);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("검색 조건 쿼리")
    public void test01() {
        //given


        //when
        List<Member> members = query.select(member)
            .from(member)
            .where(member.name.eq("name1"))
            .fetch();

        //then
        members.forEach(System.out::println);
    }

    @Test
    @DisplayName("페이징과 정렬")
    public void test02() {
        //given


        //when
        List<Member> members = query.selectFrom(member)
            .orderBy(member.age.desc())
            .offset(1)
            .limit(10)
            .fetch();

        //then
        members.forEach(System.out::println);
    }

    @Test
    @DisplayName("그룹")
    public void test03() {
        //given


        //when
        List<Member> members = query.selectFrom(member)
            .groupBy(member.id)
            .having(member.age.eq(30))
            .fetch();

        //then
        members.forEach(System.out::println);
    }

    @Test
    @DisplayName("조인1")
    public void test04() {
        //given


        //when
        List<Member> members = query.selectFrom(member)
            .join(member.team, team)
            .fetch();

        //then
        members.forEach(System.out::println);
    }

    @Test
    @DisplayName("조인2")
    public void test05() {
        //given


        //when
        List<Member> members = query.selectFrom(member)
            .leftJoin(member.team, team)
            .on(member.age.eq(30))
            .fetch();

        //then
        members.forEach(System.out::println);
    }

    @Test
    @DisplayName("프로젝션")
    public void test06() {
        //given


        //when
        List<String> names = query.select(member.name)
            .from(member)
            .fetch();

        List<UserDto> userDtos = query.select(Projections.constructor(UserDto.class, member.name, member.age))
            .from(member)
            .fetch();

        //then
        names.forEach(System.out::println);
        userDtos.forEach(System.out::println);
    }

    @Test
    @DisplayName("수정 배치 쿼리")
    public void test07() {
        //given


        //when
        query.update(member)
            .set(member.age, 100)
            .execute();

        //then
    }

    @Test
    @DisplayName("삭제 배치 쿼리")
    public void test08() {
        //given


        //when
        query.delete(member)
            .where(member.age.eq(20))
            .execute();

        //then
    }

    @Test
    @DisplayName("동적 쿼리")
    public void test09() {
        //given

        //when
        List<Member> members = query.select(member)
            .from(member)
            .where(nameEq("name1"))
            .fetch();

        //then
    }

    BooleanExpression nameEq(String name) {
        if (name == null || name.isEmpty()) return null;
        return member.name.eq(name);
    }

    @Test
    @DisplayName("서브 쿼리리")
    public void test10() {
        //given


        //when
        List<Member> members = query.select(member)
            .from(member)
            .where(member.age.in(JPAExpressions.select(member.age)
                .from(member)
                .where(member.id.eq(1L))))
            .fetch();


        //then
    }

}
