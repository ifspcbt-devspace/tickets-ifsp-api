package br.com.ifsp.tickets.domain.shared.exceptions;

public class IllegalResourceAccess extends DomainException {

    public IllegalResourceAccess() {
        super("The authenticated user does not have permission to access this resource");
    }

    public IllegalResourceAccess(String message) {
        super(message);
    }
}
