package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.event.EventService;
import br.com.ifsp.tickets.app.event.EventServiceFactory;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
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
    private final ITicketSaleGateway ticketSaleGateway;
    private final IFileStorage fileStorage;
    private final IDomainEventPublisher eventPublisher;

    @Bean
    public EventService eventService() {
        return EventServiceFactory.create(companyGateway, eventGateway, ticketSaleGateway, fileStorage, eventPublisher);
    }
}
