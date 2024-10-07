package br.com.ifsp.tickets.app.enrollment.core.create;

import br.com.ifsp.tickets.domain.user.User;

import java.time.LocalDate;

public record CreateEnrollmentInput(
        String userId,
        String name,
        String email,
        String document,
        LocalDate birthDate,
        String eventId,
        String ticketSaleId,
        String ticketId
) {
    public static CreateEnrollmentInput of(String userId, String name, String email, String document, LocalDate birthDate, String eventId, String ticketSaleId, String ticketId) {
        return new CreateEnrollmentInput(userId, name, email, document, birthDate, eventId, ticketSaleId, ticketId);
    }
}
