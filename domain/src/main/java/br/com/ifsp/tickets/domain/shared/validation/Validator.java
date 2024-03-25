package br.com.ifsp.tickets.domain.shared.validation;

public abstract class Validator {

    private final IValidationHandler handler;

    protected Validator(final IValidationHandler anHandler) {
        this.handler = anHandler;
    }

    public abstract void validate();

    protected IValidationHandler validationHandler() {
        return this.handler;
    }

    protected void error(Error error) {
        this.handler.append(error);
    }

    protected void error(String message) {
        this.handler.append(new Error(message));
    }

    protected void validationHandler(IValidationHandler validationHandler) {
        this.handler.append(validationHandler);
    }
}
