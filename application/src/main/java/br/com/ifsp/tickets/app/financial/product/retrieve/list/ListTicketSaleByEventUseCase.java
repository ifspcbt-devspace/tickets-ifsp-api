package br.com.ifsp.tickets.app.financial.product.retrieve.list;

import br.com.ifsp.tickets.app.financial.product.retrieve.Ticket2SellOutput;
import br.com.ifsp.tickets.domain.administrative.event.EventID;
import br.com.ifsp.tickets.domain.financial.product.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;

public class ListTicketSaleByEventUseCase implements IListTicketSaleByEventUseCase{
    private final ITicketSaleGateway gateway;

    public ListTicketSaleByEventUseCase(ITicketSaleGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Pagination<Ticket2SellOutput> execute(String eventId) {
        return this.gateway.findAllByEventID(EventID.with(eventId), new SearchQuery(0, 10, null, "id", "desc")).map(Ticket2SellOutput::from);
    }
}
