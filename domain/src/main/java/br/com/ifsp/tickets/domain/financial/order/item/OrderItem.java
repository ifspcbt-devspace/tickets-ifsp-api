package br.com.ifsp.tickets.domain.financial.order.item;

import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.financial.product.TicketSale;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderItem extends Entity<OrderItemID> {

    private final OrderID orderID;
    private final TicketSale ticket;
    private final int quantity;

    public OrderItem(OrderItemID id, OrderID orderID, TicketSale ticket, int quantity) {
        super(id);
        this.orderID = orderID;
        this.ticket = ticket;
        this.quantity = quantity;
    }

    public static OrderItem with(OrderItemID id, OrderID orderID, TicketSale ticket, int quantity) {
        return new OrderItem(id, orderID, ticket, quantity);
    }

    public static OrderItem newOrderItem(OrderID orderID, TicketSale ticket, int quantity) {
        return new OrderItem(new OrderItemID(null), orderID, ticket, quantity);
    }

    public BigDecimal getTotal() {
        return ticket.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public void validate(IValidationHandler handler) {
        new OrderItemValidator(handler, this).validate();
    }
}
