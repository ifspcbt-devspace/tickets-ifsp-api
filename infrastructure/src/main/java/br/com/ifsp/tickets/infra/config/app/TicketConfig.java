package br.com.ifsp.tickets.infra.config.app;

import br.com.ifsp.tickets.app.administrative.ticket.TicketService;
import br.com.ifsp.tickets.app.administrative.ticket.TicketServiceFactory;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.administrative.ticket.ITicketGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class TicketConfig {

    private final IEventGateway eventGateway;
    private final ITicketGateway ticketGateway;
    private final IDomainEventPublisher eventPublisher;

    @Bean
    public TicketService ticketService() {
        return TicketServiceFactory.create(eventGateway, ticketGateway, eventPublisher);
    }
}
