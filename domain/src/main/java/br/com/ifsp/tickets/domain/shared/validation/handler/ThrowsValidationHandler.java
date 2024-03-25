package br.com.ifsp.tickets.domain.shared.validation.handler;

import br.com.ifsp.tickets.domain.shared.exceptions.DomainException;
import br.com.ifsp.tickets.domain.shared.validation.Error;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements IValidationHandler {

    @Override
    public IValidationHandler append(final Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public IValidationHandler append(final IValidationHandler anHandler) {
        throw DomainException.with(anHandler.getErrors());
    }

    @Override
    public <T> T validate(final Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final Exception ex) {
            throw DomainException.with(new Error(ex.getMessage()));
        }
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }

}
