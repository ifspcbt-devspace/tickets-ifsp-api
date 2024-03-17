package br.com.ifsp.tickets.domain.ticket;

import br.com.ifsp.tickets.domain.enrollment.EnrollmentStatus;
import br.com.ifsp.tickets.domain.shared.exceptions.InvalidEnumException;
import lombok.Getter;

@Getter
public enum TicketStatus {

    AVAILABLE(1, "Disponível"),
    CONSUMED(2, "Consumido"),
    EXPIRED(3, "Expirado"),
    CANCELED(4, "Cancelado");

    private final Integer code;
    private final String description;

    TicketStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static TicketStatus fromCode(Integer code) {
        if (code == null) return null;

        return switch (code) {
            case 1 -> AVAILABLE;
            case 2 -> CONSUMED;
            case 3 -> EXPIRED;
            case 4 -> CANCELED;
            default -> throw new InvalidEnumException(TicketStatus.class, code);
        };
    }

    public boolean isAvailable() {
        return this.equals(AVAILABLE);
    }

    public boolean isConsumed() {
        return this.equals(CONSUMED);
    }

    public boolean isExpired() {
        return this.equals(EXPIRED);
    }

    public boolean isCanceled() {
        return this.equals(CANCELED);
    }

}
