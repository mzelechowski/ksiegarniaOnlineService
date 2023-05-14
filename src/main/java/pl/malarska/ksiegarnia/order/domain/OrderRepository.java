package pl.malarska.ksiegarnia.order.domain;

import pl.malarska.ksiegarnia.catalog.domain.Book;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    List<Order> findAll();
    Optional<Order> findById(Long id);
}
