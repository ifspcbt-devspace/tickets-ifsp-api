package br.com.ifsp.tickets.app.ticket.check;

import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.ticket.Ticket;
import br.com.ifsp.tickets.domain.ticket.TicketID;
import br.com.ifsp.tickets.domain.user.User;

public class CheckTicketUseCase implements ICheckTicketUseCase {
    private final ITicketGateway ticketGateway;
    private final IEventGateway eventGateway;

    public CheckTicketUseCase(ITicketGateway ticketGateway, IEventGateway eventGateway) {
        this.ticketGateway = ticketGateway;
        this.eventGateway = eventGateway;
    }

    @Override
    public void execute(CheckTicketInput anIn) {
        final User user = anIn.user();
        final TicketID ticketID = TicketID.with(anIn.id());
        final Ticket ticket = this.ticketGateway.findById(ticketID).orElseThrow(() -> NotFoundException.with(Ticket.class, ticketID));
        final EventID eventID = ticket.getEventID();
        final Event event = this.eventGateway.findById(eventID).orElseThrow(() -> NotFoundException.with(Event.class, eventID));

        if ((!user.canManageTickets() || !user.getCompanyID().equals(event.getCompanyID())) && !user.canManageAnyTicket())
            throw new IllegalResourceAccessException("You don't have permission to check this ticket");

        ticket.consume(event);
        this.ticketGateway.update(ticket);
    }
}
