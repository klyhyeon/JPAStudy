//package learn.jpa.model;
//
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.QueryResults;
//import com.querydsl.core.Tuple;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import learn.jpa.dto.ItemDto;
//import learn.jpa.dto.SearchParam;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.util.ObjectUtils;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.PersistenceUnit;
//import java.util.List;
//
//import static learn.jpa.model.QChair.chair;
//import static learn.jpa.model.QItem.item;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class QuerydslTest {
//    @PersistenceUnit
//    EntityManagerFactory emf;
//    EntityManager em;
//    EntityTransaction tx;
//    JPAQueryFactory jpaQueryFactory;
//
//    private Member member;
//
//    @BeforeEach
//    void setUp() {
//        em = emf.createEntityManager();
//        tx = em.getTransaction();
//        jpaQueryFactory = new JPAQueryFactory(em);
//        tx.begin();
//        Address address = Address.builder()
//                .city("city")
//                .street("street")
//                .zipcode("zipcode")
//                .build();
//
//        Address comAddress = Address.builder()
//                .city("cocity")
//                .street("costreet")
//                .zipcode("cozipcode")
//                .build();
//
//        Period period = Period.of("20210714", "20210714");
//
//        member = Member.builder()
//                .name("kim")
//                .age(26)
//                .period(period)
//                .homeAddress(address)
//                .companyAddress(comAddress)
//                .build();
//
//        em.persist(member);
//
//        Item item1 = Item.builder().price(1000).name("item1").build();
//        Item item2 = Item.builder().price(100).name("item2").build();
//        Item item3 = Item.builder().price(200).name("item3").build();
//        Item item4 = Item.builder().price(300).name("item4").build();
//        Item item5 = Item.builder().price(400).name("item5").build();
//        Chair chair = Chair.builder().name("item3").count(1000).build();
//
//        em.persist(item1);
//        em.persist(item2);
//        em.persist(item3);
//        em.persist(item4);
//        em.persist(item5);
//        em.persist(chair);
//
//        em.flush();
//    }
//
//    @Test
//    @DisplayName("QueryDSL 시작")
//    void querydslTest() {
//        QMember qMember = new QMember("m"); //생성된 별칭
//        List<Member> members = jpaQueryFactory.selectFrom(qMember)
//                .where(qMember.name.eq("kim")).orderBy(qMember.id.desc()).fetch(); //책에서 다른 부분
//
//        assertThat(members.get(0).getName()).isEqualTo("kim");
//        assertThat(members.get(0).getAge()).isEqualTo(26);
//    }
//
//    @Test
//    @DisplayName("페이징 테스트")
//    void pagingTest() {
//        QItem item = QItem.item;
//
//        List<Item> result = jpaQueryFactory.selectFrom(item).where(item.price.lt(500))
//                                           .orderBy(item.price.desc(), item.name.desc())
//                                           .offset(1).limit(3).fetch();
//
//        result.forEach(r -> System.out.println(r.toString()));
//
//        assertThat(result.get(0).getName()).isEqualTo("item4");
//    }
//
//    @Test
//    @DisplayName("조회 결과 테스트")
//    void listResultsTest() {
//        QueryResults<Item> result = jpaQueryFactory.selectFrom(item).where(item.price.lt(500))
//                .orderBy(item.price.desc(), item.name.desc())
//                .offset(1).limit(3).fetchResults();
//
//        long total = result.getTotal(); //500보다 작은수 총 count
//        long limit = result.getLimit(); //limit 3
//        long offset = result.getOffset(); // offset 1
//        List<Item> results = result.getResults();
//
//        assertThat(total).isEqualTo(4L);
//        assertThat(limit).isEqualTo(3L);
//        assertThat(offset).isEqualTo(1L);
//
//        results.forEach(r -> System.out.println(r.toString()));
//    }
//
//    @Test
//    @DisplayName("groupBy 테스트")
//    void groupByTest() {
//        jpaQueryFactory.selectFrom(item)
//                       .groupBy(item.price)
//                       .having(item.price.lt(500))
//                       .fetch();
//    }
//
//    @Test
//    @DisplayName("join 테스트")
//    void joinTest(){
//        jpaQueryFactory.selectFrom(item).join(chair).on(chair.name.eq(item.name)).fetch();
//    }
//
//    @Test
//    @DisplayName("subQuery 테스트")
//    void subQueryTest() {
//        jpaQueryFactory.selectFrom(item)
//                .where(item.name.eq(String.valueOf(JPAExpressions.selectFrom(chair).where(chair.name.eq("item3")))))
//                .fetch();
//    }
//
//    @Test
//    @DisplayName("프로젝션 테스트")
//    void projectionTest() {
//        List<Tuple> list = jpaQueryFactory.select(item.name, item.price).from(item).orderBy(item.name.desc()).fetch();
//    }
//
//    @Test
//    @DisplayName("projection dto")
//    void dtoTest() {
//        List<ItemDto> result = jpaQueryFactory.select(Projections.bean(ItemDto.class, item.name.as("name"), item.price)).from(item).fetch();
//    }
//
//    @Test
//    @DisplayName("동적 쿼리")
//    void 동적쿼리_Test() {
//        SearchParam param = new SearchParam();
//        param.setName(null);
//        param.setPrice(200);
//
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        if (!ObjectUtils.isEmpty(param.getName())) {
//            System.out.println(param.getName());
//            booleanBuilder.and(item.name.eq(param.getName()));
//        }
//
//        if (!ObjectUtils.isEmpty(param.getPrice())) {
//            System.out.println(param.getPrice());
//            booleanBuilder.and(item.price.eq(param.getPrice()));
//        }
//
//        List<Item> result = jpaQueryFactory.selectFrom(item).where(booleanBuilder).fetch();
//    }
//}
