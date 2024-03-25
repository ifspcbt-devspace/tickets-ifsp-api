package br.com.ifsp.tickets.domain.shared.exceptions;

public class IllegalTicketCodeException extends DomainException {

    public IllegalTicketCodeException(String ticketCode) {
        super("Ticket code '%s' is not valid".formatted(ticketCode));
    }

}
