package br.com.ifsp.tickets.app.administrative.ticket;

import br.com.ifsp.tickets.app.administrative.ticket.check.CheckTicketUseCase;
import br.com.ifsp.tickets.app.administrative.ticket.check.ICheckTicketUseCase;
import br.com.ifsp.tickets.app.administrative.ticket.retrieve.get.GetTicketByIDUseCase;
import br.com.ifsp.tickets.app.administrative.ticket.retrieve.get.IGetTicketByIDUseCase;
import br.com.ifsp.tickets.app.administrative.ticket.retrieve.list.IListTicketsByUserUseCase;
import br.com.ifsp.tickets.app.administrative.ticket.retrieve.list.ListTicketsByUserUseCase;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.administrative.ticket.ITicketGateway;

public class TicketServiceFactory {
    private static TicketService ticketService;

    public static TicketService create(
            IEventGateway eventGateway,
            ITicketGateway ticketGateway,
            IDomainEventPublisher eventPublisher
    ) {
        if (ticketService == null) {
            final ICheckTicketUseCase checkTicketUseCase = new CheckTicketUseCase(ticketGateway, eventGateway, eventPublisher);
            final IGetTicketByIDUseCase getTicketByIDUseCase = new GetTicketByIDUseCase(ticketGateway, eventGateway);
            final IListTicketsByUserUseCase listTicketsByUserUseCase = new ListTicketsByUserUseCase(ticketGateway);
            ticketService = new TicketService(checkTicketUseCase, getTicketByIDUseCase, listTicketsByUserUseCase);
        }
        return ticketService;
    }
}
