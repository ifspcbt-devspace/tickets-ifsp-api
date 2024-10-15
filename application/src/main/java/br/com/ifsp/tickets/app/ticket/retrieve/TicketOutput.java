package br.com.ifsp.tickets.app.ticket.retrieve;

import br.com.ifsp.tickets.domain.ticket.Ticket;
import br.com.ifsp.tickets.domain.ticket.TicketStatus;
import br.com.ifsp.tickets.domain.user.vo.RG;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TicketOutput(
        String id,
        String userId,
        String document,
        String eventId,
        String ticketSaleId,
        String description,
        LocalDate validIn,
        LocalDate expiredIn,
        TicketStatus status,
        String code,
        LocalDateTime lastTimeConsumed
) {

    public static TicketOutput from(Ticket ticket) {
        return new TicketOutput(
                ticket.getId().getValue().toString(),
                ticket.getUserID().isEmpty() ? null : ticket.getUserID().get().getValue().toString(),
                new RG(ticket.getDocument()).getInitials(),
                ticket.getEventID().getValue().toString(),
                ticket.getTicketSaleID().getValue().toString(),
                ticket.getDescription(),
                ticket.getValidIn(),
                ticket.getExpiredIn(),
                ticket.getStatus(),
                ticket.getCode().getCode(),
                ticket.getLastTimeConsumed()
        );
    }
}
