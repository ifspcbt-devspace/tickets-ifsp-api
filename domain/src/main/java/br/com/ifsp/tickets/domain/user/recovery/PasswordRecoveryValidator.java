package br.com.ifsp.tickets.domain.user.recovery;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.validation.Validator;

public class PasswordRecoveryValidator extends Validator {

    private final PasswordRecovery token;

    public PasswordRecoveryValidator(IValidationHandler anHandler, PasswordRecovery token) {
        super(anHandler);
        this.token = token;
    }

    @Override
    public void validate() {
        this.validateUser();
        this.validateToken();
        this.validateCreatedAt();
        this.validateExpiresAt();
    }

    private void validateUser() {
        if (token.getUser() == null)
            error("User is required");
    }

    private void validateToken() {
        if (token.getToken() == null || token.getToken().isBlank())
            error("Token is required");
    }

    private void validateCreatedAt() {
        if (token.getCreatedAt() == null)
            error("Created at is required");
    }

    private void validateExpiresAt() {
        if (token.getExpiresAt() == null)
            error("Expires at is required");
    }
}
