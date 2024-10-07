package br.com.ifsp.tickets.domain.event.sale;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;

import java.util.Optional;

public interface ITicketSaleGateway {

    TicketSale create(TicketSale ticketSale);

    Optional<TicketSale> findById(TicketSaleID id);

    Pagination<TicketSale> findAllByEventID(EventID id, SearchQuery sq);

    Pagination<TicketSale> findAll(AdvancedSearchQuery sq);

    TicketSale update(TicketSale event);

    void delete(TicketSale ticketSale);

    boolean exists(TicketSaleID id);
}
