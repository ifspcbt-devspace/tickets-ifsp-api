package br.com.ifsp.tickets.domain.shared.exceptions;

public class ArealdyUsedTokenException extends DomainException {

    public ArealdyUsedTokenException() {
        super("The recovery token has already been used");
    }
}
