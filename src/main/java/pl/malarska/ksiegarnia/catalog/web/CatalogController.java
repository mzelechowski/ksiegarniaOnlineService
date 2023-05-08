package pl.malarska.ksiegarnia.catalog.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.malarska.ksiegarnia.catalog.application.port.CatalogUseCase;
import pl.malarska.ksiegarnia.catalog.domain.Book;

import java.util.List;

@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class CatalogController {
    private CatalogUseCase catalog;

    @GetMapping
    public List<Book> getAll() {
        return catalog.findAll();
    }

}
