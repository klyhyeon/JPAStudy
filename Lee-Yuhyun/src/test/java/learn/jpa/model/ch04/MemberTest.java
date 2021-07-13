package learn.jpa.model.ch04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("ch4 Member DDL 테스트")
    @Transactional
    public void memberTest() {

        MemberCh4 member = MemberCh4.builder()
                                    .id("1").build();

        testEntityManager.persist(member);
    }

}