package br.com.ifsp.tickets.app.administrative.event.retrieve.search;

import br.com.ifsp.tickets.app.IUseCase;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public interface ISearchEventUseCase extends IUseCase<AdvancedSearchQuery, Pagination<SearchEventOutput>> {
}
