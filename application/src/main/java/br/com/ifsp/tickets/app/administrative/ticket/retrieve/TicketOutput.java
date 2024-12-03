package br.com.ifsp.tickets.app.administrative.ticket.retrieve;

import br.com.ifsp.tickets.app.administrative.enrollment.core.retrieve.EnrollmentOutput;
import br.com.ifsp.tickets.domain.administrative.ticket.Ticket;
import br.com.ifsp.tickets.domain.administrative.ticket.TicketStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TicketOutput(
        String id,
        EnrollmentOutput enrollment,
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
                EnrollmentOutput.from(ticket.getEnrollment()),
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
