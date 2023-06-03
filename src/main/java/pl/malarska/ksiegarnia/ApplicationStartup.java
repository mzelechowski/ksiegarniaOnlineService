package pl.malarska.ksiegarnia;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.malarska.ksiegarnia.catalog.db.AuthorJpaRepository;
import pl.malarska.ksiegarnia.catalog.application.port.CatalogUseCase;
import pl.malarska.ksiegarnia.catalog.domain.Author;
import pl.malarska.ksiegarnia.catalog.domain.Book;
import pl.malarska.ksiegarnia.order.application.port.PlaceOrderUseCase;
import pl.malarska.ksiegarnia.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import pl.malarska.ksiegarnia.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import pl.malarska.ksiegarnia.order.application.port.QueryOrderUseCase;
import pl.malarska.ksiegarnia.order.domain.OrderItem;
import pl.malarska.ksiegarnia.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.Set;

import static pl.malarska.ksiegarnia.catalog.application.port.CatalogUseCase.*;

@Component
@AllArgsConstructor
public class ApplicationStartup implements CommandLineRunner {
    private final CatalogUseCase catalog;
    private final PlaceOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final AuthorJpaRepository authorJpaRepository;

    @Override
    public void run(String... args) {
        initData();
        placeOrder();
    }

    private void placeOrder() {
        Book effectiveJava = catalog.findOneByTitle("Effective Java").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book puzzlers = catalog.findOneByTitle("Java Puzzlers").orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        Recipient recipientJson = Recipient
                .builder()
                .name("Json Hibernate")
                .phone("997")
                .street("Rolnicza 15")
                .city("Wroblekowo")
                .zipCode("12-345")
                .email("json@email.com")
                .build();

        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipientJson)
                .item(new OrderItem(effectiveJava.getId(), 16))
                .item(new OrderItem(puzzlers.getId(), 7))
                .build();
        PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created ORDER with id: " + response.getOrderId());


        Recipient recipientDeveloper = Recipient
                .builder()
                .name("Maciej Zelechowski")
                .phone("609580000")
                .street("Warszawska 32")
                .city("Łomianki")
                .zipCode("05-092")
                .email("mike@gmail.com")
                .build();
        command = PlaceOrderCommand
                .builder()
                .recipient(recipientDeveloper)
                .item(new OrderItem(effectiveJava.getId(), 5))
                .item(new OrderItem(puzzlers.getId(), 7))
                .build();
        response = placeOrder.placeOrder(command);
        System.out.println("Created ORDER with id: " + response.getOrderId());

        queryOrder.findAll()
                .forEach(order -> {
                    System.out.println("GOT ORDER WITH TOTAL PRICE: " + 0 + " DETAILS: " + order);
                });
    }

    private void initData() {
        Author joshua = new Author("Joshua", "Bloch");
        Author neal = new Author("Neal", "Gafter");
        authorJpaRepository.save(joshua);
        authorJpaRepository.save(neal);

        CreateBookCommand effectiveJava = new CreateBookCommand(
                "Effective Java",
                Set.of(joshua.getId()),
                2005,
                new BigDecimal("79.00")
        );

        CreateBookCommand javaPuzzlers = new CreateBookCommand(
                "Java Puzzlers",
                Set.of(joshua.getId(), neal.getId()),
                2018,
                new BigDecimal("99")
        );
        catalog.addBook(effectiveJava);
        catalog.addBook(javaPuzzlers);
    }

}
