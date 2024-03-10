package br.com.ifsp.tickets.domain.shared.exceptions;

public class InvalidEmailException extends DomainException {

    public InvalidEmailException(String email) {
        super("Email '%s' is not valid".formatted(email));
    }
}
