package br.com.ifsp.tickets.domain.user.email;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.validation.Validator;

public class UpsertEmailValidator extends Validator {

    private final UpsertEmail upsertEmail;

    public UpsertEmailValidator(IValidationHandler anHandler, UpsertEmail upsertEmail) {
        super(anHandler);
        this.upsertEmail = upsertEmail;
    }

    @Override
    public void validate() {
        this.validateEmail();
        this.validateRequestDate();
        this.validateUser();
        this.validateToken();
        this.validateLastNotificationDate();
    }

    private void validateEmail() {
        if (upsertEmail.getEmail() == null) {
            error("'email' is required");
        }
    }

    private void validateRequestDate() {
        if (upsertEmail.getRequestDate() == null) {
            error("'requestDate' is required");
        }
    }

    private void validateUser() {
        if (upsertEmail.getUser() == null) {
            error("'user' is required");
        }
    }

    private void validateToken() {
        if (upsertEmail.getToken() == null) {
            error("'token' is required");
        }
    }

    private void validateLastNotificationDate() {
        if (upsertEmail.getLastNotificationDate() == null) {
            error("'lastNotification' date is required");
        }
    }
}
