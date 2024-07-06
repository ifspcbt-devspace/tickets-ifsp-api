package br.com.ifsp.tickets.infra.contexts.event.sale;

import br.com.ifsp.tickets.domain.event.EventID;
import br.com.ifsp.tickets.domain.event.sale.ITicketSaleGateway;
import br.com.ifsp.tickets.domain.event.sale.TicketSale;
import br.com.ifsp.tickets.domain.event.sale.TicketSaleID;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TicketSaleGateway implements ITicketSaleGateway {

    @Override
    public TicketSale create(TicketSale ticketSale) {
        return null;
    }

    @Override
    public Optional<TicketSale> findById(TicketSaleID id) {
        return Optional.empty();
    }

    @Override
    public Pagination<TicketSale> findAllByEventID(EventID id, SearchQuery sq) {
        return null;
    }

    @Override
    public Pagination<TicketSale> findAll(SearchQuery sq) {
        return null;
    }

    @Override
    public Pagination<TicketSale> findAll(AdvancedSearchQuery sq) {
        return null;
    }

    @Override
    public TicketSale update(TicketSale event) {
        return null;
    }

    @Override
    public void delete(TicketSale ticketSale) {

    }

    @Override
    public boolean exists(TicketSaleID id) {
        return false;
    }
}
