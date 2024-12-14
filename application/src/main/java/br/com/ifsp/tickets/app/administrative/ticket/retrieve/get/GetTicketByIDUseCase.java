package br.com.ifsp.tickets.app.administrative.ticket.retrieve.get;

import br.com.ifsp.tickets.app.administrative.ticket.retrieve.TicketOutput;
import br.com.ifsp.tickets.domain.administrative.enrollment.Enrollment;
import br.com.ifsp.tickets.domain.administrative.event.Event;
import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.administrative.ticket.ITicketGateway;
import br.com.ifsp.tickets.domain.administrative.ticket.Ticket;
import br.com.ifsp.tickets.domain.administrative.ticket.TicketID;
import br.com.ifsp.tickets.domain.administrative.user.User;

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
        final Enrollment enrollment = ticket.getEnrollment();
        final boolean isUserTicket = enrollment.getUserID().isPresent() ? enrollment.getUserID().get().equals(user.getId()) : enrollment.getDocument().equalsIgnoreCase(user.getDocument().getValue());
        if (isUserTicket || user.canManageAnyTicket())
            return TicketOutput.from(ticket);

        final Event event = this.eventGateway.findById(ticket.getEventID()).orElseThrow(() -> NotFoundException.with(Event.class, ticket.getEventID()));
        if (user.getCompanyID().equals(event.getCompanyID()) && user.canManageTickets())
            return TicketOutput.from(ticket);

        throw new IllegalResourceAccessException("You don't have permission to access this ticket");
    }
}
