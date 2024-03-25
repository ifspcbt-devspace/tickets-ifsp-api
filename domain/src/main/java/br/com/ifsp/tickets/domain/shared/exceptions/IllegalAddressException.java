package br.com.ifsp.tickets.domain.shared.exceptions;

public class IllegalAddressException extends DomainException{

    public IllegalAddressException(String aMessage) {
        super(aMessage);
    }
}
