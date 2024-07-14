package br.com.ifsp.tickets.app.enrollment.create;

import br.com.ifsp.tickets.domain.user.User;

public record CreateEnrollmentInput(
        User user,
        String eventId
) {
    public static CreateEnrollmentInput of(User aggregate, String s) {
        return new CreateEnrollmentInput(aggregate, s);
    }
}
