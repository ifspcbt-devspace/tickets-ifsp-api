package br.com.ifsp.tickets.domain.shared.event;

import br.com.ifsp.tickets.domain.shared.DomainEventType;
import br.com.ifsp.tickets.domain.shared.IDomainEvent;
import br.com.ifsp.tickets.domain.ticket.Ticket;

import java.time.Instant;
import java.util.UUID;

public class TicketConsumedSuccess implements IDomainEvent {

    private final String targetId;

    public TicketConsumedSuccess(Ticket ticket) {
        this.targetId = ticket.getId().getValue().toString();
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }

    @Override
    public String subject() {
        return "TicketConsumed";
    }

    @Override
    public String message() {
        return "Ticket has been consumed";
    }

    @Override
    public String reason() {
        return "";
    }

    @Override
    public String source() {
        return Ticket.class.getName();
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
