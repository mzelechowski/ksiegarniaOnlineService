package pl.malarska.ksiegarnia.catalog.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.malarska.ksiegarnia.catalog.application.port.CatalogUseCase;
import pl.malarska.ksiegarnia.catalog.domain.Book;

import java.util.List;
import java.util.Optional;


@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class CatalogController {
    private CatalogUseCase catalog;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAll(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> author
    ) {
        if (title.isPresent() && author.isPresent()) {
            return catalog.findByTitleAndAuthor(title.get(), author.get());
        } else if (title.isPresent()) {
            return catalog.findByTitle(title.get());
        } else if (author.isPresent()) {
            return catalog.findByAuthor(author.get());
        } else {
            return catalog.findAll();
        }
    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<Book> getAll() {
//        return catalog.findAll();
//    }

//    @GetMapping(params ={"title"})
//    @ResponseStatus(HttpStatus.OK)
//    public List<Book> getAllFiltered(@RequestParam String title) {
//        return catalog.findByTitle(title);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return catalog
                .findById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }

}
