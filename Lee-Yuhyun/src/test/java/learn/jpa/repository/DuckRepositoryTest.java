package learn.jpa.repository;

import learn.jpa.model.ch14.Duck;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DuckRepositoryTest {

    @Autowired
    private DuckRepository duckRepository;

    @Test
    @DisplayName("리스너 테스트")
    void listnerTest() {
        Duck duck = Duck.builder()
                        .name("yaduck").build();
        duckRepository.save(duck);
    }

}