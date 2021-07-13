package learn.jpa.model.ch09;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.transaction.Transactional;


@DataJpaTest
class MemberEmbeddedTest {

    @Autowired
    private TestEntityManager testEm; //JPA 테스트에 사용되는 EntityManager 대체 클래스입니다.

    @Test
    @DisplayName("값 타입 공유 test")
    @Transactional
    public void homeAddressWrong() {
        MemberEmbedded member1 = new MemberEmbedded();
        MemberEmbedded member2 = new MemberEmbedded();

        member1.setHomeAddress(Address.builder()
                                    .city("OldCity").build());
        Address address = member1.getHomeAddress();
        MemberEmbedded newMember1 = testEm.persist(member1); //insert

//        Address newAddress = address.clone();
//        newAddress.setCity("NewCity");
        address.setCity("NewCity");
        MemberEmbedded newMember2 = testEm.persist(member2); //insert

//        member2.setHomeAddress(newAddress);
        member2.setHomeAddress(address);
        testEm.flush();

        String member1Address = member1.getHomeAddress().getCity();
        String member2Address = member2.getHomeAddress().getCity();

        System.out.println(member1Address + " | " + member2Address);
        Assertions.assertEquals(member1Address, member2Address);
    }

    @Test
    @DisplayName("값 타입 복사 test")
    @Transactional
    public void homeAddressCorrect() {
        MemberEmbedded member1 = new MemberEmbedded();
        MemberEmbedded member2 = new MemberEmbedded();

        member1.setHomeAddress(Address.builder()
                .city("OldCity").build());
        Address address = member1.getHomeAddress();
        MemberEmbedded newMember1 = testEm.persist(member1); //insert

        Address newAddress = address.clone();
        newAddress.setCity("NewCity");
        MemberEmbedded newMember2 = testEm.persist(member2); //insert

        member2.setHomeAddress(newAddress);
        testEm.flush(); //update

        String member1Address = member1.getHomeAddress().getCity();
        String member2Address = member2.getHomeAddress().getCity();

        System.out.println(member1Address + " | " + member2Address);
        Assertions.assertEquals(member1Address, member2Address);
    }

}