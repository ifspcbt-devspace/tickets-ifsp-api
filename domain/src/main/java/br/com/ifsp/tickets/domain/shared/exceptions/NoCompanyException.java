package br.com.ifsp.tickets.domain.shared.exceptions;

public class NoCompanyException extends DomainException {

    public NoCompanyException() {
        super("The authenticated user does not have a company associated with it");
    }

    public NoCompanyException(String message) {
        super(message);
    }
}
