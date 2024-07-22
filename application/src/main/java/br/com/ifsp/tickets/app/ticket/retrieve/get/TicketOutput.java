package br.com.ifsp.tickets.app.ticket.retrieve.get;

import br.com.ifsp.tickets.domain.ticket.Ticket;
import br.com.ifsp.tickets.domain.ticket.TicketStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TicketOutput(
        String id,
        String userId,
        String eventId,
        String description,
        LocalDate validIn,
        LocalDate expiredIn,
        LocalDateTime expiredAt,
        TicketStatus status,
        String code,
        LocalDateTime lastTimeConsumed
) {

    public static TicketOutput from(Ticket ticket) {
        return new TicketOutput(
                ticket.getId().getValue().toString(),
                ticket.getUserID().getValue().toString(),
                ticket.getEventID().getValue().toString(),
                ticket.getDescription(),
                ticket.getValidIn(),
                ticket.getExpiredIn(),
                ticket.getExpiredIn().atStartOfDay(),
                ticket.getStatus(),
                ticket.getCode().getCode(),
                ticket.getLastTimeConsumed()
        );
    }
}
