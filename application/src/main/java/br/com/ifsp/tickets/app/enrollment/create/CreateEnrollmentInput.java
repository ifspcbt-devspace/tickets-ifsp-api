package br.com.ifsp.tickets.app.enrollment.create;

import br.com.ifsp.tickets.domain.user.User;

import java.time.LocalDate;

public record CreateEnrollmentInput(
        User user,
        String name,
        String email,
        String document,
        LocalDate birthDate,
        String eventId
) {
    public static CreateEnrollmentInput of(User aggregate, String name, String email, String document, LocalDate birthDate, String eventId) {
        return new CreateEnrollmentInput(aggregate, name, email, document, birthDate, eventId);
    }
}
