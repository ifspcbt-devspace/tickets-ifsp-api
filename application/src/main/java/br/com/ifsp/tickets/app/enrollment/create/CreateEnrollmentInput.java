package br.com.ifsp.tickets.app.enrollment.create;

import br.com.ifsp.tickets.domain.user.User;

import java.time.LocalDate;

public record CreateEnrollmentInput(
        User user,
        String name,
        String email,
        String document,
        LocalDate birthDate,
        String eventId,
        String ticketSaleId
) {
    public static CreateEnrollmentInput of(User aggregate, String name, String email, String document, LocalDate birthDate, String eventId, String ticketSaleId) {
        return new CreateEnrollmentInput(aggregate, name, email, document, birthDate, eventId, ticketSaleId);
    }
}
