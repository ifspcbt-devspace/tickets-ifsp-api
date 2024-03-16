package br.com.ifsp.tickets.domain.shared.exceptions;

import br.com.ifsp.tickets.domain.ticket.TicketID;

public class TicketExpiredException extends DomainException {

    public TicketExpiredException(TicketID ticketID) {
        super("Ticket " + ticketID.getValue() + " is expired");
    }

}
