package br.com.ifsp.tickets.domain.financial.order;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.validation.Validator;

public class OrderValidator extends Validator {

    private final Order order;

    public OrderValidator(IValidationHandler anHandler, Order order) {
        super(anHandler);
        this.order = order;
    }

    @Override
    public void validate() {
        this.validateStatus();
        this.validateCreatedAt();
        this.validateName();
        this.validateEmail();
        this.validateDocument();
        this.validateBirthDate();
        this.validatePhoneNumber();
    }

    private void validateStatus() {
        if (this.order.getStatus() == null) {
            this.error("Status is required");
        }
    }

    private void validateCreatedAt() {
        if (this.order.getCreatedAt() == null) {
            this.error("CreatedAt is required");
        }
    }

    private void validateName() {
        if (this.order.getName() == null || this.order.getName().isBlank()) {
            this.error("Name is required");
        }
    }

    private void validateEmail() {
        if (this.order.getEmail() == null) {
            this.error("Email is required");
        }
    }

    private void validateDocument() {
        if (this.order.getDocument() == null) {
            this.error("Document is required");
        }
    }

    private void validateBirthDate() {
        if (this.order.getBirthDate() == null) {
            this.error("BirthDate is required");
        }
    }

    private void validatePhoneNumber() {
        if (this.order.getPhoneNumber() == null) {
            this.error("PhoneNumber is required");
        }
    }

}
