package pl.malarska.ksiegarnia.catalog.application.port;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.malarska.ksiegarnia.catalog.domain.Author;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {
}
