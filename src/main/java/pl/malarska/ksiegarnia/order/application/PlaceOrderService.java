package pl.malarska.ksiegarnia.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.malarska.ksiegarnia.order.application.port.PlaceOrderUseCase;
import pl.malarska.ksiegarnia.order.db.OrderJpaRepository;
import pl.malarska.ksiegarnia.order.domain.Order;

@Service
@RequiredArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {
    private final OrderJpaRepository repository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order
                .builder()
                .recipient(command.getRecipient())
                .items(command.getItems())
                .build();
        Order save = repository.save(order);
        return PlaceOrderResponse.success(save.getId());
    }

    @Override
    public PlaceOrderResponse updateStatusOrder(UpdateStatusOrderCommand command) {
        Order order = Order
                .builder()
                .id(command.getId())
                .status(command.getStatus())
                .recipient(command.getRecipient())
                .items(command.getItems())
                .createdAt(command.getCreatedAt())
                .build();
        Order save = repository.save(order);
        return PlaceOrderResponse.success(save.getId());
    }

    @Override
    public void deleteOrderById(Long id) {
        repository.deleteById(id);
    }
}
