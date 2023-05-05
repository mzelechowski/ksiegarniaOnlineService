package pl.malarska.ksiegarnia.catalog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.malarska.ksiegarnia.catalog.application.port.CatalogUseCase;
import pl.malarska.ksiegarnia.catalog.domain.Book;
import pl.malarska.ksiegarnia.catalog.domain.CatalogRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

    private final CatalogRepository repository;

//    //dodano adnotację @Primary w repozytorium
//    public CatalogService(CatalogRepository repository){
//        this.repository=repository;
//    }

//    public CatalogService(@Qualifier("schoolCatalogRepository") CatalogRepository repository){
//        this.repository=repository;
//    }

    @Override
    public List<Book> findByTitle(String title) {
        findAll();
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().startsWith(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getAuthor().startsWith(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll(){
        return repository.findAll();
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author){
        return Optional.empty();
    }

    @Override
    public void addBook(CreateBookCommand command){
        Book book = new Book(command.getTitle(), command.getAuthor(), command.getYear());
        repository.save(book);
    }

    @Override
    public void removeById(Long id){

    }

    @Override
    public void updateBook(){

    }
}