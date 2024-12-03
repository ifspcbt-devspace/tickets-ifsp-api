package br.com.ifsp.tickets.app.financial.order.retrieve.list;

import br.com.ifsp.tickets.app.financial.order.retrieve.OrderOutput;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.vo.Document;
import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchFilter;

public class ListCustomerOrdersUseCase implements IListCustomerOrdersUseCase {

    private final IOrderGateway orderGateway;

    public ListCustomerOrdersUseCase(IOrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public Pagination<OrderOutput> execute(ListCustomerOrdersInput anIn) {
        final User user = anIn.authenticatedUser();
        final Document document = user.getDocument();

        final AdvancedSearchQuery query = anIn.advancedSearchQuery();
        query.filters().removeIf(searchFilter -> searchFilter.filterKey().equals("document"));
        query.filters().removeIf(searchFilter -> searchFilter.filterKey().equals("customer"));

        query.filters().add(new SearchFilter("document", document.toString(), "eq", "all"));

        return this.orderGateway.findAll(query).map(OrderOutput::from);
    }
}
