package br.com.ifsp.tickets.domain.administrative.event;

import br.com.ifsp.tickets.domain.shared.exceptions.IllegalEnumException;
import lombok.Getter;

@Getter
public enum EventStatus {

    SCHEDULED(1, "Agendado"),
    PUBLISHED(2, "Publicado"),
    OPENED(3, "Aberto"),
    IN_PROGRESS(4, "Em andamento"),
    CANCELED(5, "Cancelado"),
    FINISHED(6, "Finalizado");

    private final Integer code;
    private final String description;

    EventStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static EventStatus fromCode(Integer code) {
        if (code == null) return null;

        return switch (code) {
            case 1 -> SCHEDULED;
            case 2 -> PUBLISHED;
            case 3 -> OPENED;
            case 4 -> IN_PROGRESS;
            case 5 -> CANCELED;
            case 6 -> FINISHED;
            default -> throw new IllegalEnumException(EventStatus.class, code);
        };
    }

    public boolean isScheduled() {
        return this.equals(SCHEDULED);
    }

    public boolean isPublished() {
        return this.equals(PUBLISHED);
    }

    public boolean isCanceled() {
        return this.equals(CANCELED);
    }

    public boolean isFinished() {
        return this.equals(FINISHED);
    }

    public boolean isOpened() {
        return this.equals(OPENED);
    }

    public boolean isInProgress() {
        return this.equals(IN_PROGRESS);
    }

}
