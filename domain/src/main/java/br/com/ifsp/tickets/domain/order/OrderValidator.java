package br.com.ifsp.tickets.domain.order;

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
        this.validateTicket();
        this.validateQuantity();
        this.validateStatus();
        this.validateCreatedAt();
    }

    private void validateTicket() {
        if (this.order.getTicket() == null) {
            this.error("Ticket is required");
        }
    }

    private void validateQuantity() {
        if (this.order.getQuantity() <= 0) {
            this.error("Quantity must be greater than 0");
        }
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
}
