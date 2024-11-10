package br.com.ifsp.tickets.domain.shared;

import java.io.Serializable;
import java.time.Instant;

public interface IDomainEvent extends Serializable {

    Instant occurredOn();

    String subject();

    String message();

    String reason();

    String source();

    DomainEventType type();

    String id();



}
