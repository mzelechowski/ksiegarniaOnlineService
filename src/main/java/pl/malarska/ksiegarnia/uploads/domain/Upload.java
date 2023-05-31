package pl.malarska.ksiegarnia.uploads.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Upload {
    @Id
    @GeneratedValue
    private Long id;
    private byte[] file;
    private String contentType;
    private String fileName;
    @CreatedDate
    private LocalDateTime createAt;

    public Upload(String fileName, byte[] file, String contentType) {
        this.fileName = fileName;
        this.file = file;
        this.contentType = contentType;
    }
}
