package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.administrative.event.EventService;
import br.com.ifsp.tickets.app.administrative.event.EventServiceFactory;
import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.shared.file.IFileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventConfig {

    private final ICompanyGateway companyGateway;
    private final IEventGateway eventGateway;
    private final IFileStorage fileStorage;
    private final IDomainEventPublisher eventPublisher;

    @Bean
    public EventService eventService() {
        return EventServiceFactory.create(companyGateway, eventGateway, fileStorage, eventPublisher);
    }
}
