package pl.malarska.ksiegarnia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.malarska.ksiegarnia.catalog.application.port.CatalogUseCase;
import pl.malarska.ksiegarnia.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import pl.malarska.ksiegarnia.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import pl.malarska.ksiegarnia.catalog.domain.Book;
import pl.malarska.ksiegarnia.order.application.port.PlaceOrderUseCase;
import pl.malarska.ksiegarnia.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import pl.malarska.ksiegarnia.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import pl.malarska.ksiegarnia.order.application.port.QueryOrderUseCase;
import pl.malarska.ksiegarnia.order.domain.OrderItem;
import pl.malarska.ksiegarnia.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {
    private final CatalogUseCase catalog;
    private final PlaceOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final String title;
    private final Long limit;

    public ApplicationStartup(CatalogUseCase catalog,
                              PlaceOrderUseCase placeOrder,
                              QueryOrderUseCase queryOrder,
                              @Value("${ksiegarnia.catalog.query}") String title,
                              @Value("${ksiegarnia.catalog.limit:3}") Long limit) {
        this.catalog = catalog;
        this.placeOrder = placeOrder;
        this.queryOrder = queryOrder;
        this.title = title;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        initData();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        Book harry = catalog.findOneByTitle("Harry Potter").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book sezon = catalog.findOneByTitle("Sezon Burz").orElseThrow(() -> new IllegalStateException("Cannot find a book"));

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
                .item(new OrderItem(harry, 16))
                .item(new OrderItem(sezon, 7))
                .build();
        PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created ORDER with id: " + response.getOrderId());

        Book java = catalog.findOneByTitle("Java").orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book php = catalog.findOneByTitle("PHP").orElseThrow(() -> new IllegalStateException("Cannot find a book"));

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
                .item(new OrderItem(java, 5))
                .item(new OrderItem(php, 7))
                .build();
        response = placeOrder.placeOrder(command);
        System.out.println("Created ORDER with id: " + response.getOrderId());

        queryOrder.findAll()
                .forEach(order -> {
                    System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + " DETAILS: " + order);
                });

    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
    }

    private void initData() {
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Harry Potter", "JK Rowling", 1998, new BigDecimal("15.50")));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Wladca Pierścieni", "JRR Tolkien", 1954, new BigDecimal("15.50")));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Mężczyźni którzy nieniawidzą kobiet", "Stieg Larsson", 2005, new BigDecimal("15.50")));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Sezon Burz", "Andrzej Sapkowski", 2013, new BigDecimal("15.50")));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Wladca Pierścieni, Dwie wieże", "JRR Tolkien", 1956, new BigDecimal("15.50")));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Java leksykon", "Marcin Lis", 2007, new BigDecimal("17.50")));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("PHP5", "Steve Nowicki", 2005, new BigDecimal("79.00")));
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        books.stream().limit(3).forEach(System.out::println);
    }

    private void findAndUpdate() {
        System.out.println("Updating book ..........");
        catalog.findOneByTitleAndAuthor("Wladca", "JRR")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand.builder()
                            .id(book.getId())
                            .title("Wladca Pierscieni Druzyna Pierscienia")
                            .build();
                    UpdateBookResponse updateBookResponse = catalog.updateBook(command);
                    System.out.println("Updating book result: " + updateBookResponse.isSuccess());
                });


    }
}
