package br.com.ifsp.tickets.app.ticket;

import br.com.ifsp.tickets.app.ticket.check.CheckTicketUseCase;
import br.com.ifsp.tickets.app.ticket.check.ICheckTicketUseCase;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;

public class TicketServiceFactory {
    private static TicketService ticketService;

    public static TicketService create(
            IEventGateway eventGateway,
            ITicketGateway ticketGateway
    ) {
        if (ticketService == null) {
            final ICheckTicketUseCase checkTicketUseCase = new CheckTicketUseCase(ticketGateway, eventGateway);

            ticketService = new TicketService(checkTicketUseCase);
        }
        return ticketService;
    }
}
