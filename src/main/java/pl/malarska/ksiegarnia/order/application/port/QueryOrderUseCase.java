package pl.malarska.ksiegarnia.order.application.port;

import pl.malarska.ksiegarnia.catalog.domain.Book;
import pl.malarska.ksiegarnia.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {
    List<Order> findAll();

    Optional<Order> findById(Long id);
}
