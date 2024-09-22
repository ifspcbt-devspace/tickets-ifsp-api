package br.com.ifsp.tickets.domain.event;

import br.com.ifsp.tickets.domain.shared.ValueObject;
import br.com.ifsp.tickets.domain.shared.exceptions.EventThumbnailException;
import br.com.ifsp.tickets.domain.shared.file.FileContextType;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class EventThumbnail extends ValueObject {

    private static final String DEFAULT_EXTRA_PATH = "thumbnails";
    private final String filename;
    private final LocalDateTime uploadedAt;

    private EventThumbnail(String filename, LocalDateTime uploadedAt) {
        this.filename = filename;
        this.uploadedAt = uploadedAt;
    }

    public static EventThumbnail empty() {
        return new EventThumbnail(null, null);
    }

    public static EventThumbnail with(String filename, LocalDateTime uploadedAt, EventID eventID, IFileStorage fileUploader) {
        if (filename != null && !fileUploader.existsFile(FileContextType.EVENT, filename, DEFAULT_EXTRA_PATH, eventID.getValue().toString())) {
            throw new EventThumbnailException(eventID, filename);
        }
        return new EventThumbnail(filename, uploadedAt);
    }

    public static EventThumbnail upload(String filename, byte[] content, EventID eventID, IFileStorage fileUploader) {
        if (filename == null || content == null) return empty();
        if (fileUploader.uploadFile(FileContextType.EVENT, filename, content, DEFAULT_EXTRA_PATH, eventID.getValue().toString()))
            return new EventThumbnail(filename, LocalDateTime.now());
        return empty();
    }

    public Optional<byte[]> download(EventID eventID, IFileStorage fileUploader) {
        if (isEmpty()) return Optional.empty();
        return fileUploader.downloadFile(FileContextType.EVENT, filename, DEFAULT_EXTRA_PATH, eventID.getValue().toString());
    }

    public void delete(EventID eventID, IFileStorage fileUploader) {
        if (!isEmpty())
            fileUploader.deleteFile(FileContextType.EVENT, filename, DEFAULT_EXTRA_PATH, eventID.getValue().toString());
    }

    public boolean isEmpty() {
        return this.filename == null;
    }

}
