package br.com.ifsp.tickets.domain.shared;

@FunctionalInterface
public interface DomainEventPublisher {

    void publishEvent(DomainEvent event);

}
