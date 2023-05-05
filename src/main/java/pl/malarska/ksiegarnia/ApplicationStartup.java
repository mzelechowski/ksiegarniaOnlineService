package pl.malarska.ksiegarnia;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.malarska.ksiegarnia.catalog.application.CatalogController;
import pl.malarska.ksiegarnia.catalog.domain.Book;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {
    private final CatalogController catalogController;
    private final String title;
    private final Long limit;

    public ApplicationStartup(CatalogController catalogController,
                              @Value("${ksiegarnia.catalog.query}") String title,
                              @Value("${ksiegarnia.catalog.limit:3}") Long limit) {
        this.catalogController = catalogController;
        this.title = title;
        this.limit=limit;
    }

    @Override
    public void run(String... args) {
        List<Book> books = catalogController.findByTitle(title);
        books.stream().limit(limit).forEach(System.out::println);

        books = catalogController.findByAuthor("Henryk");
        books.stream().limit(limit).forEach(System.out::println);
    }
}
