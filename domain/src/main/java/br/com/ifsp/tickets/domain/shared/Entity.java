package br.com.ifsp.tickets.domain.shared;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;
    private final List<IDomainEvent> IDomainEvents;

    protected Entity(final ID id) {
        this(id, null);
    }

    protected Entity(final ID id, final List<IDomainEvent> IDomainEvents) {
        Objects.requireNonNull(id, "entity 'id' should not be null");
        this.id = id;
        this.IDomainEvents = new ArrayList<>(IDomainEvents == null ? Collections.emptyList() : IDomainEvents);
    }

    public abstract void validate(IValidationHandler handler);

    public ID getId() {
        return id;
    }

    public List<IDomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(IDomainEvents);
    }

    public void publishDomainEvents(final IDomainEventPublisher publisher) {
        if (publisher == null) {
            return;
        }
        getDomainEvents().forEach(publisher::publishEvent);
        this.IDomainEvents.clear();
    }

    public void registerEvent(final IDomainEvent event) {
        if (event == null) {
            return;
        }
        this.IDomainEvents.add(event);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return getId().equals(entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
