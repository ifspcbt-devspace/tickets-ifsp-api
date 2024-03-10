package br.com.ifsp.tickets.domain.shared;

import java.io.Serializable;
import java.time.Instant;

public interface DomainEvent extends Serializable {

    Instant occurredOn();

    String subject();

}
