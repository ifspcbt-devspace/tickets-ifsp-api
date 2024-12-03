package br.com.ifsp.tickets.domain.financial.order.item;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.validation.Validator;

public class OrderItemValidator extends Validator {

    private final OrderItem orderItem;

    public OrderItemValidator(IValidationHandler anHandler, OrderItem orderItem) {
        super(anHandler);
        this.orderItem = orderItem;
    }

    @Override
    public void validate() {
        if (this.orderItem.getOrderID() == null) {
            this.error("OrderID is required");
        }

        if (this.orderItem.getTicket() == null) {
            this.error("Ticket is required");
        }

        if (this.orderItem.getQuantity() <= 0) {
            this.error("Quantity must be greater than 0");
        }
    }
}
