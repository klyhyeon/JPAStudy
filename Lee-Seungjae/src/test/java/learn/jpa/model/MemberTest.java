package learn.jpa.model;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import learn.jpa.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("real")
class MemberTest {

    @PersistenceUnit //J2EE환경에서는 이렇게 팩토리를 얻을 수 있다.
    EntityManagerFactory emf;
    // 책에 나온 Persistence.createEntityManagerFactory("");

    EntityManager em;
    EntityTransaction tx;

    @Autowired
    private MemberRepository memberRepository;

    Address address;
    Address comAddress;
    Period period;

    @BeforeEach
    void connectJPA() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        address = Address.builder()
            .city("city")
            .street("street")
            .zipcode("zipcode")
            .build();

        comAddress = Address.builder()
            .city("cocity")
            .street("costreet")
            .zipcode("cozipcode")
            .build();

        period = Period.of("20210714", "20210714");
    }

//    @AfterEach
//    void closeAll() { //JPA 반드시 종료 무조건 시켜주자
//        tx.commit();
//        em.close();
//        emf.close();
//    }

    @Test
    @DisplayName("2장 테스트")
    void memberTest() {

        //when
        Member member = Member.builder()
            .name("홍길동")
            .age(30)
            .build();

        //등록
        em.persist(member);

        em.flush();

        Member insertData = em.find(Member.class, member.getId());

        // then
        assertThat(insertData.getName()).isEqualTo(member.getName());
        assertThat(insertData.getAge()).isEqualTo(member.getAge());
    }


    @Test
    @DisplayName("프록시 테스트")
    void proxyTest() {
        Member resultMember = em.find(Member.class, 1L);
        em.flush();

        assertThat(resultMember.getName()).isEqualTo("홍길동");
        assertThat(resultMember.getAge()).isEqualTo(27);
    }

    @Test
    @DisplayName("9장 테스트")
    void embeddedTest() {
        Address address = Address.builder()
            .city("city")
            .street("street")
            .zipcode("zipcode")
            .build();

        Address comAddress = Address.builder()
            .city("cocity")
            .street("costreet")
            .zipcode("cozipcode")
            .build();

        Period period = Period.of("20210714", "20210714");

        Member member = Member.builder()
            .name("name")
            .age(26)
            .period(period)
            .homeAddress(address)
            .companyAddress(comAddress)
            .build();

        em.persist(member);
        em.flush();

        System.out.println(member.toString());
    }

    @Test
    void 두건_조회_테스트() {
        Address address = Address.builder()
            .city("city")
            .street("street")
            .zipcode("zipcode")
            .build();

        Address comAddress = Address.builder()
            .city("cocity")
            .street("costreet")
            .zipcode("cozipcode")
            .build();

        Period period = Period.of("20210714", "20210714");

        Member member = Member.builder()
            .name("name")
            .age(26)
            .period(period)
            .homeAddress(address)
            .companyAddress(comAddress)
            .build();

        Member member2 = Member.builder()
            .name("name")
            .age(26)
            .period(period)
            .homeAddress(address)
            .companyAddress(comAddress)
            .build();

        memberRepository.save(member);
        memberRepository.save(member2);

        memberRepository.findByName("name");
    }
}