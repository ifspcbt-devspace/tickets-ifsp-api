package br.com.ifsp.tickets.app.event.sale.create;

import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.event.sale.TicketSale;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.exceptions.NoCompanyException;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;
import br.com.ifsp.tickets.domain.shared.validation.handler.Notification;
import br.com.ifsp.tickets.domain.user.User;

import java.math.BigDecimal;

public class CreateTicket2SellUseCase implements ICreateTicket2SellUseCase {

    private final ITicketSaleGateway ticketSaleGateway;
    private final IEventGateway eventGateway;

    public CreateTicket2SellUseCase(ITicketSaleGateway ticketSaleGateway, IEventGateway eventGateway) {
        this.ticketSaleGateway = ticketSaleGateway;
        this.eventGateway = eventGateway;
    }

    @Override
    public CreateTicket2SellOutput execute(CreateTicket2SellInput anIn) {
        final User user = anIn.user();
        final EventID eventID = EventID.with(anIn.eventId());
        final Event event = this.eventGateway.findById(eventID).orElseThrow(() -> NotFoundException.with(Event.class, eventID));
        final CompanyID companyID = event.getCompanyID();
        if (!user.canManageEvents() && !user.canManageAnyEvent())
            throw new IllegalResourceAccessException("User does not have permission to sale tickets for events");
        if (!user.hasCompany() && !user.canManageAnyEvent()) throw new NoCompanyException();
        if (!user.canManageAnyEvent() && !user.getCompanyID().equals(companyID))
            throw new IllegalResourceAccessException("User does not have permission to sale tickets for this event");

        final String name = anIn.name();
        final String description = anIn.description();
        final BigDecimal price = anIn.price();
        final int entries = anIn.entries();
        final TicketSale ticketSale = TicketSale.newTicketSale(event, name, description, price, entries);
        final Notification notification = Notification.create("An error occurred while validating a ticket sale");
        ticketSale.validate(notification);
        notification.throwPossibleErrors();
        return CreateTicket2SellOutput.from(this.ticketSaleGateway.create(ticketSale));
    }
}
