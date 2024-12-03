package br.com.ifsp.tickets.domain.shared.validation.handler;

import br.com.ifsp.tickets.domain.shared.exceptions.DomainException;
import br.com.ifsp.tickets.domain.shared.exceptions.ValidationException;
import br.com.ifsp.tickets.domain.shared.validation.Error;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Notification implements IValidationHandler {

    private final List<Error> errors;
    private String message = "";

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(String message) {
        return new Notification(new ArrayList<>()).message(message);
    }

    public static Notification create(final Throwable t) {
        return create(new Error(t.getMessage()));
    }

    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    public Notification message(String message) {
        this.message = message;
        return this;
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final IValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    public Notification append(final String message) {
        return append(new Error(message));
    }

    @Override
    public <T> T validate(final Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return null;
    }

    public Notification uniqueness(Supplier<Boolean> exists, String field) {
        if (exists.get()) append("Already exists a record with the same " + field + " value.");
        return this;
    }


    public Notification throwAnyErrors() throws ValidationException {
        if (this.hasError()) throw new ValidationException(this.message, this);
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

}
