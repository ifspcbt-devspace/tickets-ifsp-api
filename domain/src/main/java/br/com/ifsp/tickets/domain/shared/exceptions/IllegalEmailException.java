package br.com.ifsp.tickets.domain.shared.exceptions;

public class IllegalEmailException extends DomainException {

    public IllegalEmailException(String email) {
        super("Email '%s' is not valid".formatted(email));
    }
}
