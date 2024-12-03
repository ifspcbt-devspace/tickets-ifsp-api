package br.com.ifsp.tickets.domain.administrative.user.recovery;

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
        this.validateIpAddress();
        this.validateAgent();
        this.validateToken();
        this.validateCreatedAt();
        this.validateExpiresAt();
    }


    private void validateUser() {
        if (token.getUser() == null)
            error("User is required");
    }

    private void validateIpAddress() {
        if (token.getIpAddress() == null || token.getIpAddress().isBlank())
            error("'ipAddress' is required");
    }

    private void validateAgent() {
        if (token.getAgent() == null || token.getAgent().isBlank())
            error("'agent' is required");
    }

    private void validateToken() {
        if (token.getToken() == null || token.getToken().isBlank())
            error("'token' is required");
    }

    private void validateCreatedAt() {
        if (token.getCreatedAt() == null)
            error("'createdAt' is required");
    }

    private void validateExpiresAt() {
        if (token.getExpiresAt() == null)
            error("'expiresAt' is required");
    }
}
