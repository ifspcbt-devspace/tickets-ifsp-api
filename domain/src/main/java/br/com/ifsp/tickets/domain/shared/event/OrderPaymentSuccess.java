package br.com.ifsp.tickets.domain.shared.event;

import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.shared.DomainEventType;
import br.com.ifsp.tickets.domain.shared.IDomainEvent;

import java.time.Instant;
import java.util.UUID;

public class OrderPaymentSuccess implements IDomainEvent {

    private final String targetId;

    public OrderPaymentSuccess(Order order) {
        this.targetId = order.getId().getValue().toString();
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }

    @Override
    public String subject() {
        return "OrderPayment";
    }

    @Override
    public String message() {
        return "Order has been approved";
    }

    @Override
    public String reason() {
        return "";
    }

    @Override
    public String source() {
        return Order.class.getName();
    }

    @Override
    public DomainEventType type() {
        return DomainEventType.INFO;
    }

    @Override
    public String targetId() {
        return this.targetId;
    }

    @Override
    public String id() {
        return UUID.randomUUID().toString();
    }
}
