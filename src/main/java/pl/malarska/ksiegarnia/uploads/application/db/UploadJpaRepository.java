package pl.malarska.ksiegarnia.uploads.application.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.malarska.ksiegarnia.uploads.domain.Upload;

public interface UploadJpaRepository extends JpaRepository<Upload, Long> {
}
