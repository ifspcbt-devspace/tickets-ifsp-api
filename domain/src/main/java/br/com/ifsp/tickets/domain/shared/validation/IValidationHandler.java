package br.com.ifsp.tickets.domain.shared.validation;

import java.util.List;

public interface IValidationHandler {

    IValidationHandler append(Error anError);

    default IValidationHandler append(String aMessage) {
        return append(new Error(aMessage));
    }

    IValidationHandler append(IValidationHandler anHandler);

    <T> T validate(Validation<T> aValidation);

    List<Error> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        if (getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().getFirst();
        } else {
            return null;
        }
    }

    interface Validation<T> {
        T validate();
    }

}
