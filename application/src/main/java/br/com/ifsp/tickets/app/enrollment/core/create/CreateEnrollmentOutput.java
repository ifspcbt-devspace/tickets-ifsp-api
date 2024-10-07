package br.com.ifsp.tickets.app.enrollment.core.create;

import br.com.ifsp.tickets.domain.communication.email.Email;
import br.com.ifsp.tickets.domain.enrollment.Enrollment;
import br.com.ifsp.tickets.domain.ticket.Ticket;

public record CreateEnrollmentOutput(
        String enrollmentId,
        String ticketId,
        String emailId
) {

    public static CreateEnrollmentOutput from(Enrollment enrollment, Ticket ticket, Email email) {
        return new CreateEnrollmentOutput(enrollment.getId().getValue().toString(), ticket.getId().getValue().toString(),
                email.getId().getValue().toString());
    }
}
