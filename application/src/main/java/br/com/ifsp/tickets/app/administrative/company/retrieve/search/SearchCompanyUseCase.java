package br.com.ifsp.tickets.app.administrative.company.retrieve.search;

import br.com.ifsp.tickets.domain.administrative.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;

public class SearchCompanyUseCase implements ISearchCompanyUseCase {

    private final ICompanyGateway companyGateway;

    public SearchCompanyUseCase(ICompanyGateway companyGateway) {
        this.companyGateway = companyGateway;
    }

    @Override
    public Pagination<SearchCompanyOutput> execute(AdvancedSearchQuery anIn) {
        return this.companyGateway.findAll(anIn).map(SearchCompanyOutput::from);
    }
}
