package br.com.ifsp.tickets.domain.shared.exceptions;

public class InvalidAddressException extends DomainException{

    public InvalidAddressException(String aMessage) {
        super(aMessage);
    }
}
