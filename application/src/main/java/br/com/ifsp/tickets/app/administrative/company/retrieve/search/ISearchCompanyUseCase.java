package br.com.ifsp.tickets.app.administrative.company.retrieve.search;

import br.com.ifsp.tickets.app.IUseCase;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public interface ISearchCompanyUseCase extends IUseCase<AdvancedSearchQuery, Pagination<SearchCompanyOutput>> {
}
