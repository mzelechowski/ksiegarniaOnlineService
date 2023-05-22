package pl.malarska.ksiegarnia.order.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.malarska.ksiegarnia.order.application.port.QueryOrderUseCase;
import pl.malarska.ksiegarnia.order.db.OrderJpaRepository;
import pl.malarska.ksiegarnia.order.domain.Order;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {
    private final OrderJpaRepository repository;

    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }

}
