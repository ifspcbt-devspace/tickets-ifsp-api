package br.com.ifsp.tickets.app.administrative.enrollment.core.create;

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
