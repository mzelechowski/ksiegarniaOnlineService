package pl.malarska.ksiegarnia.order.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.malarska.ksiegarnia.catalog.db.BookJpaRepository;
import pl.malarska.ksiegarnia.catalog.domain.Book;
import pl.malarska.ksiegarnia.order.application.port.QueryOrderUseCase;
import pl.malarska.ksiegarnia.order.db.OrderJpaRepository;
import pl.malarska.ksiegarnia.order.domain.Order;
import pl.malarska.ksiegarnia.order.domain.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class QueryOrderService implements QueryOrderUseCase {
    private final OrderJpaRepository repository;
    private final BookJpaRepository catalogRepository;

    @Override
    public List<RichOrder> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toRichOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RichOrder> findById(Long id) {
        return repository.findById(id).map(this::toRichOrder);
    }

    private RichOrder toRichOrder(Order order) {
        List<RichOrderItem> richItems = toRichItems(order.getItems());
        return new RichOrder(
                order.getId(),
                order.getStatus(),
                richItems,
                order.getRecipient(),
                order.getCreatedAt()
        );
    }

    private List<RichOrderItem> toRichItems(List<OrderItem> items) {
        return items.stream()
                .map(item -> {
                    Book book = catalogRepository
                            .findById(item.getBookId())
                            .orElseThrow(() -> new IllegalStateException("Unable to find book with ID: " + item.getBookId()));
                    return new RichOrderItem(book, item.getQuantity());
                })
                .collect(Collectors.toList());
    }
}
