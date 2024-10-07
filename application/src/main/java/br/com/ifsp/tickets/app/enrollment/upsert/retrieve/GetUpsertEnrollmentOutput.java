package br.com.ifsp.tickets.app.enrollment.upsert.retrieve;

import br.com.ifsp.tickets.domain.enrollment.EnrollmentStatus;
import br.com.ifsp.tickets.domain.enrollment.upsert.UpsertEnrollment;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.sale.TicketSaleID;
import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.user.UserID;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GetUpsertEnrollmentOutput(
        String userID,
        String name,
        String email,
        String document,
        LocalDate birthDate,
        String eventId,
        String ticketID,
        String ticketSaleId
) {

    public static GetUpsertEnrollmentOutput from(UpsertEnrollment output) {
        return new GetUpsertEnrollmentOutput(
                output.getUserID().getValue().toString(),
                output.getName(),
                output.getEmail(),
                output.getDocument(),
                output.getBirthDate(),
                output.getEventId().getValue().toString(),
                output.getTicketID().getValue().toString(),
                output.getTicketSaleId().getValue().toString()
        );
    }
}
