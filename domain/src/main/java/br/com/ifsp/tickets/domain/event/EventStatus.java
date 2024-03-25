package br.com.ifsp.tickets.domain.event;

import br.com.ifsp.tickets.domain.shared.exceptions.IllegalEnumException;
import lombok.Getter;

@Getter
public enum EventStatus {

    SCHEDULED(1, "Agendado"),
    PUBLISHED(2, "Publicado"),
    OPENED(3, "Aberto"),
    CANCELED(4, "Cancelado"),
    FINISHED(5, "Finalizado");

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
            case 4 -> CANCELED;
            case 5 -> FINISHED;
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
}
