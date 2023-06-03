package pl.malarska.ksiegarnia.catalog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.malarska.ksiegarnia.catalog.application.port.AuthorsUseCase;
import pl.malarska.ksiegarnia.catalog.db.AuthorJpaRepository;
import pl.malarska.ksiegarnia.catalog.domain.Author;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorsService implements AuthorsUseCase {
    private final AuthorJpaRepository repository;
    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }
}
