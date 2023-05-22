package pl.malarska.ksiegarnia.order.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.malarska.ksiegarnia.order.domain.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
