package br.com.ifsp.tickets.infra.contexts.administrative.event.persistence;

import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.administrative.event.vo.EventThumbnail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events_thumbnails")
@NoArgsConstructor
@Getter
public class EventThumbnailJpaEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "filename", nullable = false)
    private String filename;
    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    public EventThumbnailJpaEntity(UUID id, String filename, LocalDateTime uploadedAt) {
        this.id = id;
        this.filename = filename;
        this.uploadedAt = uploadedAt;
    }

    public static EventThumbnailJpaEntity from(EventID eventID, EventThumbnail thumbnail) {
        return new EventThumbnailJpaEntity(eventID.getValue(), thumbnail.getFilename(), thumbnail.getUploadedAt());
    }

    public EventThumbnail toVo() {
        return EventThumbnail.with(this.filename, this.uploadedAt);
    }
}
