package br.com.ifsp.tickets.domain.event;

import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchQuery;

import java.util.Optional;

public interface IEventGateway {

    Event create(Event event);

    Optional<Event> findById(EventID id);

    Pagination<Event> findAllByCompanyID(CompanyID id, SearchQuery sq);

    Pagination<Event> findAll(AdvancedSearchQuery sq);

    Event update(Event event);

    void delete(Event id);

    boolean exists(EventID id);
}
