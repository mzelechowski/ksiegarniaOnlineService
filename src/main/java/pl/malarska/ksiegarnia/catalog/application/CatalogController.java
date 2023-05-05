package pl.malarska.ksiegarnia.catalog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.malarska.ksiegarnia.catalog.domain.Book;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService service;


    public List<Book> findByTitle(String title) {
        return service.findByTitle(title);
    }

    public List<Book> findByAuthor(String author) {
        return service.findByAuthor(author);
    }

}
