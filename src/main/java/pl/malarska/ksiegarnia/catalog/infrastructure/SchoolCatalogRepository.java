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
@Primary
public
class SchoolCatalogRepository implements CatalogRepository {
    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public SchoolCatalogRepository () {
        storage.put(1L, new Book(1L, "Wentylacja", "Makowiecki", 1980));
        storage.put(2L, new Book(2L, "Pan Tadeusz", "Adam Mickiewicz", 1834));
        storage.put(3L, new Book(3L, "Kup kochance męża kwiaty", "Katarzyna Miller", 2000));
        storage.put(3L, new Book(4L, "Pan Wołodyjowski", "Henryk Sienkiewicz", 1899));
        System.out.println("Utworzyłem Beana  School Catalog Repository");
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}
