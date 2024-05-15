package br.com.ifsp.tickets.domain.order;

import br.com.ifsp.tickets.domain.event.sale.TicketSale;
import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Order extends AggregateRoot<OrderID> {

    private final User customer;
    private final TicketSale ticket;
    private final int quantity;
    private final LocalDateTime createdAt;
    private OrderStatus status;
    private LocalDateTime updatedAt;

    public Order(OrderID orderID, User customer, TicketSale ticket, int quantity, OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(orderID);
        this.customer = customer;
        this.ticket = ticket;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Order with(OrderID orderID, User customer, TicketSale ticket, int quantity, OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Order(orderID, customer, ticket, quantity, status, createdAt, updatedAt);
    }

    public static Order newOrderTicket(TicketSale ticket, User customer, int quantity) {
        return new Order(new OrderID(null), customer, ticket, quantity, OrderStatus.PENDING, LocalDateTime.now(), LocalDateTime.now());
    }

    public void confirm() {
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void refuse() {
        this.status = OrderStatus.REFUSED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.status = OrderStatus.CANCELED;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public void validate(IValidationHandler handler) {
        new OrderValidator(handler, this).validate();
    }
}
