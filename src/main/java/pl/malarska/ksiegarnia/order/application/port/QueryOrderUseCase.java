package pl.malarska.ksiegarnia.order.application.port;

import pl.malarska.ksiegarnia.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {
    List<Order> findAll();
}
