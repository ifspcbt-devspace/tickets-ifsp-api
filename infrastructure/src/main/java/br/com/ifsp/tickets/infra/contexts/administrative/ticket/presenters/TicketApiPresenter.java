package br.com.ifsp.tickets.infra.contexts.administrative.ticket.presenters;

import br.com.ifsp.tickets.app.administrative.ticket.retrieve.TicketOutput;
import br.com.ifsp.tickets.infra.contexts.administrative.enrollment.core.presenters.EnrollmentApiPresenter;
import br.com.ifsp.tickets.infra.contexts.administrative.ticket.models.TicketResponse;

public interface TicketApiPresenter {

    static TicketResponse present(TicketOutput output) {
        return new TicketResponse(
                output.id(),
                EnrollmentApiPresenter.present(output.enrollment()),
                output.eventId(),
                output.description(),
                output.validIn(),
                output.expiredIn(),
                output.status(),
                output.code(),
                output.lastTimeConsumed()
        );
    }

}
