package pl.malarska.ksiegarnia.catalog.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.malarska.ksiegarnia.catalog.application.port.AuthorsUseCase;
import pl.malarska.ksiegarnia.catalog.domain.Author;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/authors")
public class AuthorsController {
    private final AuthorsUseCase authors;

    @GetMapping
    public List<Author> findAll() {
        return authors.findAll();
    }
}
