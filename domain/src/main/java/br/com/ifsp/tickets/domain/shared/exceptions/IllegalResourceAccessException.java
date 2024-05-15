package br.com.ifsp.tickets.domain.shared.exceptions;

public class IllegalResourceAccessException extends DomainException {

    public IllegalResourceAccessException() {
        super("The authenticated user does not have permission to access this resource");
    }

    public IllegalResourceAccessException(String message) {
        super(message);
    }
}
