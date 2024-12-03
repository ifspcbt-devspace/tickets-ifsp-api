package br.com.ifsp.tickets.app.administrative.event.retrieve.search;

import br.com.ifsp.tickets.domain.administrative.event.IEventGateway;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public class SearchEventUseCase implements ISearchEventUseCase {

    private final IEventGateway eventGateway;

    public SearchEventUseCase(IEventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Pagination<SearchEventOutput> execute(AdvancedSearchQuery anIn) {
        return this.eventGateway.findAll(anIn).map(SearchEventOutput::from);
    }
}
