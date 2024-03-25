package br.com.ifsp.tickets.domain.shared;

@FunctionalInterface
public interface IDomainEventPublisher {

    void publishEvent(IDomainEvent event);

}
