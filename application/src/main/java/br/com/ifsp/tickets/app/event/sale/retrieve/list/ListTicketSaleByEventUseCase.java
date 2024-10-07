package br.com.ifsp.tickets.app.event.sale.retrieve.list;

import br.com.ifsp.tickets.app.event.sale.retrieve.Ticket2SellOutput;
import br.com.ifsp.tickets.domain.event.Event;
import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;

public class ListTicketSaleByEventUseCase implements IListTicketSaleByEventUseCase{
    private final ITicketSaleGateway gateway;

    public ListTicketSaleByEventUseCase(ITicketSaleGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Pagination<Ticket2SellOutput> execute(String eventId) {
        return this.gateway.findAllByEventID(EventID.with(eventId), new SearchQuery(0, 10, null, "createdAt", "desc")).map(Ticket2SellOutput::from);
    }
}
