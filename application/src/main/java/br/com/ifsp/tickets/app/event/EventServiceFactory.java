package br.com.ifsp.tickets.app.event;

import br.com.ifsp.tickets.app.event.create.CreateEventUseCase;
import br.com.ifsp.tickets.app.event.create.ICreateEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.get.GetEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.get.IGetEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.search.ISearchEventUseCase;
import br.com.ifsp.tickets.app.event.retrieve.search.SearchEventUseCase;
import br.com.ifsp.tickets.app.event.sale.create.CreateTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.create.ICreateTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.delete.DeleteTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.delete.IDeleteTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.retrieve.get.GetTicket2SellUseCase;
import br.com.ifsp.tickets.app.event.sale.retrieve.get.IGetTicket2SellUseCase;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.event.IEventGateway;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;

public class EventServiceFactory {

    private static EventService eventService;

    public static EventService create(ICompanyGateway companyGateway, IEventGateway eventGateway, ITicketSaleGateway ticketSaleGateway) {
        if (eventService == null) {
            final ICreateEventUseCase createEventUseCase = new CreateEventUseCase(companyGateway, eventGateway);
            final IGetEventUseCase getEventUseCase = new GetEventUseCase(eventGateway);
            final ISearchEventUseCase searchEventUseCase = new SearchEventUseCase(eventGateway);
            final ICreateTicket2SellUseCase createTicket2SellUseCase = new CreateTicket2SellUseCase(ticketSaleGateway, eventGateway);
            final IDeleteTicket2SellUseCase deleteTicket2SellUseCase = new DeleteTicket2SellUseCase(ticketSaleGateway, eventGateway);
            final IGetTicket2SellUseCase getTicket2SellUseCase = new GetTicket2SellUseCase(ticketSaleGateway);
            eventService = new EventService(createEventUseCase, getEventUseCase, searchEventUseCase, createTicket2SellUseCase, deleteTicket2SellUseCase, getTicket2SellUseCase);
        }
        return eventService;
    }
}
