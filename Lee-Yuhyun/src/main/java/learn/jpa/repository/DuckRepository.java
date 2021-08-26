package learn.jpa.repository;

import learn.jpa.model.ch14.Duck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DuckRepository extends JpaRepository<Duck, Long> {
}
