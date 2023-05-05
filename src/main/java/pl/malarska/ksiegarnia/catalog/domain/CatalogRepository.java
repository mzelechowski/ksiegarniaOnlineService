package pl.malarska.ksiegarnia.catalog.domain;

import java.util.List;

public interface CatalogRepository {
    List<Book> findAll();
}
