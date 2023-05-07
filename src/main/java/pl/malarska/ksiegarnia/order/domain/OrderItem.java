package pl.malarska.ksiegarnia.order.domain;

import lombok.Value;
import pl.malarska.ksiegarnia.catalog.domain.Book;

@Value
public class OrderItem {
    Book book;
    int quantity;
}
