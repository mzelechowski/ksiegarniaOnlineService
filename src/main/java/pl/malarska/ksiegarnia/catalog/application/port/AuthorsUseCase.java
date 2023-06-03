package pl.malarska.ksiegarnia.catalog.application.port;

import pl.malarska.ksiegarnia.catalog.domain.Author;

import java.util.List;

public interface AuthorsUseCase {
    List<Author> findAll();
}
