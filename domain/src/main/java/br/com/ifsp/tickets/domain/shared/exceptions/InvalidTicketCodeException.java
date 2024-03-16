package br.com.ifsp.tickets.domain.shared.exceptions;

public class InvalidTicketCodeException extends DomainException {

    public InvalidTicketCodeException(String ticketCode) {
        super("Ticket code '%s' is not valid".formatted(ticketCode));
    }

}
