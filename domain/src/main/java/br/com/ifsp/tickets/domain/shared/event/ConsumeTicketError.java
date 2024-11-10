package br.com.ifsp.tickets.domain.shared.event;

import br.com.ifsp.tickets.domain.shared.DomainEventType;
import br.com.ifsp.tickets.domain.shared.IDomainEvent;
import br.com.ifsp.tickets.domain.ticket.Ticket;

import java.time.Instant;

public class ConsumeTicketError implements IDomainEvent {

    private final String id;
    private final String reason;

    public ConsumeTicketError(Ticket ticket, String reason) {
        this.id = ticket.getId().getValue().toString();
        this.reason = reason;
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }

    @Override
    public String subject() {
        return "ConsumeTicket";
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
    public String id() {
        return this.id;
    }

    @Override
    public String reason() {
        return this.reason;
    }
}
