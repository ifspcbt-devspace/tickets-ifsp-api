package br.com.ifsp.tickets.domain.shared.event;

import br.com.ifsp.tickets.domain.shared.DomainEventType;
import br.com.ifsp.tickets.domain.shared.IDomainEvent;
import br.com.ifsp.tickets.domain.administrative.ticket.Ticket;

import java.time.Instant;
import java.util.UUID;

public class TicketConsumedError implements IDomainEvent {

    private final String targetId;
    private final String reason;

    public TicketConsumedError(Ticket ticket, String reason) {
        this.targetId = ticket.getId().getValue().toString();
        this.reason = reason;
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
        return "Ticket could not be consumed";
    }

    @Override
    public String source() {
        return Ticket.class.getName();
    }

    @Override
    public DomainEventType type() {
        return DomainEventType.ERROR;
    }

    @Override
    public String targetId() {
        return this.targetId;
    }

    @Override
    public String id() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String reason() {
        return this.reason;
    }
}
