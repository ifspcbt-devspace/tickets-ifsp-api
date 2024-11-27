package br.com.ifsp.tickets.domain.shared.event;

import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.shared.DomainEventType;
import br.com.ifsp.tickets.domain.shared.IDomainEvent;
import br.com.ifsp.tickets.domain.user.User;

import java.time.Instant;
import java.util.UUID;

public class EventCreated implements IDomainEvent {

    private final String targetId;
    private final String name;
    private final String authorId;

    public EventCreated(Event event, User author) {
        this.targetId = event.getId().getValue().toString();
        this.name = event.getName();
        this.authorId = author.getId().getValue().toString();
    }

    @Override
    public Instant occurredOn() {
        return Instant.now();
    }

    @Override
    public String subject() {
        return "EventCreated";
    }

    @Override
    public String message() {
        return "Event has been created";
    }

    @Override
    public String reason() {
        return "User " + authorId + " created the event '" + name + "'";
    }

    @Override
    public String source() {
        return Event.class.getName();
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
