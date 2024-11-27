package br.com.ifsp.tickets.infra.shared.event;

import br.com.ifsp.tickets.domain.shared.DomainEventType;
import br.com.ifsp.tickets.domain.shared.IDomainEvent;
import com.fasterxml.jackson.annotation.*;
import org.springframework.context.ApplicationEvent;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"timestamp"})
public class InfraAppEvent extends ApplicationEvent implements IDomainEvent {

    @JsonProperty("id")
    private final String id;
    @JsonProperty("target_id")
    private final String targetId;
    @JsonProperty("subject")
    private final String subject;
    @JsonProperty("message")
    private final String message;
    @JsonProperty("reason")
    private final String reason;
    @JsonProperty("source")
    private final String source;
    @JsonProperty("occurred_on")
    private final Instant occurredOn;
    @JsonProperty("type")
    private final DomainEventType type;

    public InfraAppEvent(Object objectSource, IDomainEvent domainEvent) {
        super(objectSource);
        this.id = domainEvent.targetId();
        this.targetId = domainEvent.targetId();
        this.subject = domainEvent.subject();
        this.message = domainEvent.message();
        this.reason = domainEvent.reason();
        this.source = domainEvent.source();
        this.occurredOn = domainEvent.occurredOn();
        this.type = domainEvent.type();
    }

    @Override
    public Instant occurredOn() {
        return this.occurredOn;
    }

    @Override
    public String subject() {
        return this.subject;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public String reason() {
        return this.reason;
    }

    @Override
    public String source() {
        return this.source;
    }

    @Override
    public DomainEventType type() {
        return this.type;
    }

    @Override
    public String targetId() {
        return this.targetId;
    }

    @Override
    public String id() {
        return this.id;
    }
}
