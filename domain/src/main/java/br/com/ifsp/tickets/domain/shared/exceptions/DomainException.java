package br.com.ifsp.tickets.domain.shared.exceptions;

import br.com.ifsp.tickets.domain.shared.validation.Error;

import java.util.List;
import java.util.stream.Collectors;

public class DomainException extends NoStacktraceException {

    protected final List<Error> errors;

    protected DomainException(final String aMessage, final List<Error> errors) {
        super(aMessage);
        this.errors = errors;
    }

    protected DomainException(final String aMessage) {
        super(aMessage);
        this.errors = List.of(new Error(aMessage));
    }

    public static DomainException with(final String errorMessage) {
        final Error error = new Error(errorMessage);
        return new DomainException(errorMessage, List.of(error));
    }

    public static DomainException with(final Error anError) {
        return new DomainException(anError.message(), List.of(anError));
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException(errors.stream().map(Error::message).collect(Collectors.joining("\n")), errors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
