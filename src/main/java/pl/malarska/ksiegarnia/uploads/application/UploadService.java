package pl.malarska.ksiegarnia.uploads.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.malarska.ksiegarnia.uploads.application.db.UploadJpaRepository;
import pl.malarska.ksiegarnia.uploads.application.ports.UploadUseCase;
import pl.malarska.ksiegarnia.uploads.domain.Upload;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UploadService implements UploadUseCase {
    private final UploadJpaRepository repository;

    @Override
    public Upload save(SaveUploadCommand command) {
        Upload upload = new Upload(
                command.getFileName(),
                command.getFile(),
                command.getContentType()
        );
        repository.save(upload);
        System.out.println("Save saved: " + upload.getFileName()+ " with id: " + upload.getId());
        return upload;
    }

    @Override
    public Optional<Upload> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }
}