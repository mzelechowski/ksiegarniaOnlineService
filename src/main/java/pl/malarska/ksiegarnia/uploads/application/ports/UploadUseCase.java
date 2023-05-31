package pl.malarska.ksiegarnia.uploads.application.ports;

import lombok.Value;
import pl.malarska.ksiegarnia.uploads.domain.Upload;

import java.util.Optional;

public interface UploadUseCase {
    Upload save(SaveUploadCommand command);
    Optional<Upload> getById(Long id);

    void removeById(Long id);

    @Value
    class SaveUploadCommand{
        String fileName;
        byte[] file;
        String contentType;

    }
}
