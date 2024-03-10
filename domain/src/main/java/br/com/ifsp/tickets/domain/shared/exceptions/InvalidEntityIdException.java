package br.com.ifsp.tickets.domain.shared.exceptions;

import br.com.ifsp.tickets.domain.shared.Entity;

public class InvalidEntityIdException extends DomainException {

    public InvalidEntityIdException(Class<? extends Entity> clazz, Object id) {
        super("%s id '%s' is not valid".formatted(clazz.getSimpleName(), id));
    }
}
