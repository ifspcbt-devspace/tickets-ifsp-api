package br.com.ifsp.tickets.domain.shared.exceptions;

import br.com.ifsp.tickets.domain.shared.Entity;

public class IllegalEntityIdException extends DomainException {

    public IllegalEntityIdException(Class<? extends Entity> clazz, Object id) {
        super("%s id '%s' is not valid".formatted(clazz.getSimpleName(), id));
    }
}
