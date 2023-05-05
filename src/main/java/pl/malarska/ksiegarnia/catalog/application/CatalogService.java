package pl.malarska.ksiegarnia.catalog.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.malarska.ksiegarnia.catalog.domain.Book;
import pl.malarska.ksiegarnia.catalog.domain.CatalogRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final CatalogRepository repository;

    //dodano adnotację @Primary w repozytorium
    public CatalogService(CatalogRepository repository){
        this.repository=repository;
    }

//    public CatalogService(@Qualifier("schoolCatalogRepository") CatalogRepository repository){
//        this.repository=repository;
//    }

    public List<Book> findByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthor(String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getAuthor().startsWith(author))
                .collect(Collectors.toList());
    }
}
