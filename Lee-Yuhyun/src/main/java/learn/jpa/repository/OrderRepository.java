package learn.jpa.repository;

import learn.jpa.model.ch10.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
