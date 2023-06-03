package pl.malarska.ksiegarnia.catalog.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.malarska.ksiegarnia.catalog.application.port.AuthorJpaRepository;
import pl.malarska.ksiegarnia.catalog.application.port.CatalogUseCase;
import pl.malarska.ksiegarnia.catalog.db.BookJpaRepository;
import pl.malarska.ksiegarnia.catalog.domain.Author;
import pl.malarska.ksiegarnia.catalog.domain.Book;
import pl.malarska.ksiegarnia.uploads.application.ports.UploadUseCase;
import pl.malarska.ksiegarnia.uploads.domain.Upload;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.malarska.ksiegarnia.uploads.application.ports.UploadUseCase.*;

@Service
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

//    private final CatalogRepository repository;
    private final BookJpaRepository repository;
    private final AuthorJpaRepository  authorRepository;
    private final UploadUseCase upload;

//    //dodano adnotację @Primary w repozytorium
//    public CatalogService(CatalogRepository repository){
//        this.repository=repository;
//    }

//    public CatalogService(@Qualifier("schoolCatalogRepository") CatalogRepository repository){
//        this.repository=repository;
//    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().startsWith(title.toLowerCase()))
                .findFirst();
    }

    @Override
    public Optional<Book> findOneByAuthor(String author) {
        return repository.findAll()
                .stream()
//                .filter(book -> book.getAuthor().toLowerCase().startsWith(author.toLowerCase()))
                .findFirst();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findAll()
                .stream()
//                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }


    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().startsWith(title.toLowerCase()))
//                .filter(book -> book.getAuthor().toLowerCase().startsWith(author.toLowerCase()))
                .findFirst();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
//                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Book addBook(CreateBookCommand command) {
        Book book = toBook(command);
        return repository.save(book);
    }

    private Book toBook(CreateBookCommand command){
        Book book = new Book(command.getTitle(), command.getYear(), command.getPrice());
        Set<Author> authors = command.getAuthors().stream()
                .map(authorId -> authorRepository
                        .findById(authorId)
                        .orElseThrow(() -> new IllegalArgumentException("Unable to find author wiht id: " + authorId)))
                .collect(Collectors.toSet());
        book.setAuthors(authors);
        return book;
    }

    @Override
    public void removeById(Long id) {
//        repository.removeById(id);
        repository.deleteById(id);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
        return repository.findById(command.getId())
                .map(book -> {
                    command.updateFields(book);
                    repository.save(book);
                    return UpdateBookResponse.SUCCESS;
                }).orElseGet(() -> new UpdateBookResponse(false, Arrays.asList("Book not found with id: " + command.getId())));
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void updateBookCover(UpdateBookCoverCommand command) {
        repository.findById(command.getId())
                .ifPresent(book -> {
                    Upload saveUpload = upload.save(new SaveUploadCommand(command.getFileName(), command.getFile(), command.getContentType()));
                    book.setCoverId(saveUpload.getId());
                    repository.save(book);
                });
    }

    @Override
    public void removeBookCover(Long id) {
        repository.findById(id).ifPresent(book -> {
            if (book.getCoverId() != null) {
                upload.removeById(book.getCoverId());
                book.setCoverId(null);
                repository.save(book);
            }
        });
    }

}