package br.com.ifsp.tickets.domain.shared.exceptions;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;

public class ValidationException extends DomainException {

    public ValidationException(final String aMessage, final IValidationHandler validation) {
        super(aMessage, validation.getErrors());
    }
}
