package br.com.ifsp.tickets.domain.shared.validation;

public abstract class Validator {

    private final ValidationHandler handler;

    protected Validator(final ValidationHandler aHandler) {
        this.handler = aHandler;
    }

    public abstract void validate();

    protected ValidationHandler validationHandler() {
        return this.handler;
    }

    protected void error(Error error) {
        this.handler.append(error);
    }

    protected void error(String message) {
        this.handler.append(new Error(message));
    }

    protected void validationHandler(ValidationHandler validationHandler) {
        this.handler.append(validationHandler);
    }
}
