package learn.jpa.repository;

import learn.jpa.model.ch12.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findByName(String name, Pageable pageable);

}
