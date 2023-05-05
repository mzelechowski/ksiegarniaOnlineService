package pl.malarska.ksiegarnia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.malarska.ksiegarnia.catalog.application.port.CatalogUseCase;
import pl.malarska.ksiegarnia.catalog.domain.Book;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {
    private final CatalogUseCase catalog;
    private final String title;
    private final Long limit;

    public ApplicationStartup(CatalogUseCase catalog,
                              @Value("${ksiegarnia.catalog.query}") String title,
                              @Value("${ksiegarnia.catalog.limit:3}") Long limit) {
        this.catalog = catalog;
        this.title = title;
        this.limit=limit;
    }

    @Override
    public void run(String... args) {
        initData();
        findByTitle();
    }

    private void initData() {
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Harry Potter, komnata tajemnic", "JK Rowling", 1998));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Wladca Pierścieni", "JRR Tolkien", 1954));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Mężczyźni którzy nieniawidzą kobiet", "Stieg Larsson", 2005));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Sezon Burz", "Andrzej Sapkowski", 2013));
        catalog.addBook(new CatalogUseCase.CreateBookCommand("Wladca Pierścieni, Dwie wieże", "JRR Tolkien", 1956));
    }

    private void findByTitle() {
        List<Book> books = catalog.findByTitle(title);
        books.stream().limit(1).forEach(System.out::println);
    }
}
