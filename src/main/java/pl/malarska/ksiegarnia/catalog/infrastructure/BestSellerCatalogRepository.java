package pl.malarska.ksiegarnia.catalog.infrastructure;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.malarska.ksiegarnia.catalog.domain.Book;
import pl.malarska.ksiegarnia.catalog.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public
class BestSellerCatalogRepository implements CatalogRepository {
    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public BestSellerCatalogRepository() {
        storage.put(1L, new Book(1L, "Harry Potter, komnata tajemnic", "JK Rowling", 1998));
        storage.put(2L, new Book(2L, "Władca Pierścieni", "JRR Tolkien", 1954));
        storage.put(3L, new Book(3L, "Mężczyźni którzy nieniawidzą kobiet", "Stieg Larsson", 2005));
        storage.put(4L, new Book(4L, "Sezon Burz", "Andrzej Sapkowski", 2013));
        System.out.println("Utworzyłem Beana BestSellerRepository");
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}
