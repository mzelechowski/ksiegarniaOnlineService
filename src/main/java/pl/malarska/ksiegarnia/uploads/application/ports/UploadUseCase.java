package pl.malarska.ksiegarnia.uploads.application.ports;

import lombok.Value;
import pl.malarska.ksiegarnia.uploads.domain.Upload;

import java.util.Optional;

public interface UploadUseCase {
    Upload save(SaveUploadCommand command);
    Optional<Upload> getById(String id);

    void removeById(String id);

    @Value
    class SaveUploadCommand{
        String id;
        byte[] file;
        String contentType;

    }
}
