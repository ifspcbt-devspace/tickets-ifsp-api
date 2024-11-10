package br.com.ifsp.tickets.infra.shared.event;

import br.com.ifsp.tickets.domain.shared.IDomainEvent;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class DomainEventPublisher implements IDomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publishEvent(IDomainEvent event) {
        this.publisher.publishEvent(new InfraAppEvent(this, event));
    }
}
