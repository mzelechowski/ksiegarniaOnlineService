package pl.malarska.ksiegarnia.order.application.port;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.malarska.ksiegarnia.order.domain.Order;
import pl.malarska.ksiegarnia.order.domain.OrderRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {
    private final OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll();
    }
}
