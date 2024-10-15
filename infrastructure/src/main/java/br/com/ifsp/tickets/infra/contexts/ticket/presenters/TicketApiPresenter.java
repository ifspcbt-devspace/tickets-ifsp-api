package br.com.ifsp.tickets.infra.contexts.ticket.presenters;

import br.com.ifsp.tickets.app.ticket.retrieve.TicketOutput;
import br.com.ifsp.tickets.infra.contexts.ticket.models.TicketResponse;

public interface TicketApiPresenter {

    static TicketResponse present(TicketOutput output) {
        return new TicketResponse(
                output.id(),
                output.userId(),
                output.document(),
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
