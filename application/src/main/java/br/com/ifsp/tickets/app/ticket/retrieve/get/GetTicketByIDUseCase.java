package br.com.ifsp.tickets.app.ticket.retrieve.get;

import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.ticket.Ticket;
import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.user.User;

public class GetTicketByIDUseCase implements IGetTicketByIDUseCase {

    private final ITicketGateway ticketGateway;
    private final IEventGateway eventGateway;

    public GetTicketByIDUseCase(ITicketGateway ticketGateway, IEventGateway eventGateway) {
        this.ticketGateway = ticketGateway;
        this.eventGateway = eventGateway;
    }

    @Override
    public TicketOutput execute(GetTicketInput anIn) {
        final User user = anIn.user();
        final TicketID ticketID = TicketID.with(anIn.ticketID());
        final Ticket ticket = this.ticketGateway.findById(ticketID).orElseThrow(() -> NotFoundException.with(Ticket.class, ticketID));
        if (ticket.getUserID().equals(user.getId()) || user.canManageAnyTicket())
            return TicketOutput.from(ticket);

        final Event event = this.eventGateway.findById(ticket.getEventID()).orElseThrow(() -> NotFoundException.with(Event.class, ticket.getEventID()));
        if (user.getCompanyID().equals(event.getCompanyID()) && user.canManageTickets())
            return TicketOutput.from(ticket);

        throw new IllegalResourceAccessException("You don't have permission to access this ticket");
    }
}
