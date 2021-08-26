package learn.jpa.repository;

import learn.jpa.model.ch12.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("Pageable 테스트")
    void pageableTest() {
        //페이징 조건과 정렬 조건 설정
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.by("id").ascending());
        //given
        String name = "yuhyeon";
        Page<Student> result = studentRepository.findByName(name, pageRequest);
        List<Student> students = result.getContent();
        int totalPages = result.getTotalPages();
        boolean hasNextPage = result.hasNext();
    }

}