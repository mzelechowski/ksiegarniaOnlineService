package pl.malarska.ksiegarnia.catalog.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.malarska.ksiegarnia.catalog.domain.Book;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

}
