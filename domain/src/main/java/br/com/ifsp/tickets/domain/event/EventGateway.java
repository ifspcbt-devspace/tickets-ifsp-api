package br.com.ifsp.tickets.domain.event;

import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;

public interface EventGateway {

    Event create(Event event);

    Event findById(EventID id);

    Pagination<Event> findAllByCompanyID(CompanyID id);

    Pagination<Event> findAll(SearchQuery sq);

    Event update(Event event);

    void delete(Event id);

    boolean exists(EventID id);
}
