package br.com.ifsp.tickets.app.event.sale.delete;

import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.event.sale.TicketSale;
import br.com.ifsp.tickets.domain.event.sale.TicketSaleID;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NoCompanyException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.user.User;

public class DeleteTicket2SellUseCase implements IDeleteTicket2SellUseCase {

    private final ITicketSaleGateway ticketSaleGateway;
    private final IEventGateway eventGateway;

    public DeleteTicket2SellUseCase(ITicketSaleGateway ticketSaleGateway, IEventGateway eventGateway) {
        this.ticketSaleGateway = ticketSaleGateway;
        this.eventGateway = eventGateway;
    }

    @Override
    public void execute(DeleteTicket2SellInput anIn) {
        final User user = anIn.user();
        final TicketSaleID ticketSaleID = TicketSaleID.with(anIn.ticketId());
        final TicketSale sale = this.ticketSaleGateway.findById(ticketSaleID).orElseThrow(() -> NotFoundException.with(TicketSale.class, ticketSaleID));
        final Event event = this.eventGateway.findById(sale.getEventID()).orElseThrow(() -> NotFoundException.with(TicketSale.class, ticketSaleID));

        if (!user.canManageEvents() && !user.canManageAnyEvent())
            throw new IllegalResourceAccessException("User does not have permission to sale tickets for events");
        if (!user.hasCompany() && !user.canManageAnyEvent()) throw new NoCompanyException();
        if (!user.canManageAnyEvent() && !user.getCompanyID().equals(event.getCompanyID()))
            throw new IllegalResourceAccessException("User does not have permission to sale tickets for this event");

        this.ticketSaleGateway.delete(sale);
    }

}
