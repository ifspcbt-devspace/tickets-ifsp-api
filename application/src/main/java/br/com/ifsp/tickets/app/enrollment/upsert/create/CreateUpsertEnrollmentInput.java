package br.com.ifsp.tickets.app.enrollment.upsert.create;

import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.user.User;

import java.time.LocalDate;

public record CreateUpsertEnrollmentInput(
        User user,
        String name,
        String email,
        String document,
        LocalDate birthDate,
        String eventId,
        String ticketSaleId,
        String ticketID,
        String preferenceURL
) {
    public static CreateUpsertEnrollmentInput of(User aggregate, String name, String email, String document, LocalDate birthDate, String eventId, String ticketSaleId, String preferenceURL, String ticketID) {
        return new CreateUpsertEnrollmentInput(aggregate, name, email, document, birthDate, eventId, ticketSaleId, ticketID, preferenceURL);
    }
}
